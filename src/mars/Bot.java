package mars;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.stream.Collectors;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Collection;
import jade.util.leap.Iterator;
import onto.DepositProposal;
import onto.DepositProposalRequest;
import onto.DepositProposalResponse;
import onto.MarsOntology;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import sajas.core.Agent;
import sajas.core.behaviours.Behaviour;
import sajas.core.behaviours.FSMBehaviour;
import sajas.core.behaviours.OneShotBehaviour;
import sajas.core.behaviours.ParallelBehaviour;
import sajas.core.behaviours.SimpleBehaviour;
import sajas.domain.DFService;
import sajas.proto.ContractNetInitiator;
import sajas.proto.ContractNetResponder;

// classe do agente
public abstract class Bot extends Entity {
	
	
	private double energy;       // The energy level of the agent
	private double heading;      // The heading in degrees of the agent
	private Base b;
	private List<Mineral> planned = new ArrayList<>();
	
	private static int nextid = 1;
	private int id;
	private ACLMessage mycfp;
	private SLCodec codec;
	private Ontology onto;
	private FSMBehaviour fsm;
	public Bot(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x, double y, Base b){
		super(context, cs, grid, maxspeed, x, y);
		this.b = b;
		id = nextid++;
		System.out.println("Bot " + id + " Created");
		energy = EntityGlobals.getMaxEnergy();
		
	}
	private static final int OUT_OF_ENERGY = 1;
	private static final int RECHARGED = 2;
	private static final int FOUND_MINERAL = 3;
	private static final int NO_PLAN = 4;
	private static final int HAVE_PLANNED = 5;
	private static final int NEW_CONTRACT = 6;
	private static final int FINISHED_CONTRACT = 7;
	private static final int GO_TO_MINERAL = 8;
	
	private static final String WANDER_B = "Wander";
	private static final String WANDER_SB = "WanderSub";
	private static final String RECHARGE_B = "Recharge";
	private static final String EXECUTE_B = "Execute";
	private static final String START_INIT_CONTRACT_NET_B = "StartInitContractNet";
	private static final String INIT_CONTRACT_NET_B = "InitContractNet";
	private static final String RESPOND_CONTRACT_NET_B = "RespondContractNet";
	
	interface RetraceableBehaviour{
		public int getPreviousReturnValue();
	}
	
	abstract class InterruptableBehaviour extends SimpleBehaviour implements RetraceableBehaviour {
		
		int retval = -1;
		int prevRetVal = -1;
		public InterruptableBehaviour(String string) {
			this.setBehaviourName(string);
		}
		@Override
		public int onEnd() {
			prevRetVal = retval;
			int ret = retval;
			retval = -1;
			return ret;
		}
		@Override
		public boolean done() {
			boolean ret = retval != -1;
			return ret;
		}
		public int getPreviousReturnValue(){
			return prevRetVal;
		}
	}
	
	class PowerConsumingBehaviour extends InterruptableBehaviour {
		public PowerConsumingBehaviour(String string) {
			super(string);
		}

		public void action() {
			//System.out.println("Agent " + id + " running " + this.getBehaviourName());
			if(dist(b) > energy - maxRange()){
				retval = OUT_OF_ENERGY;
				return;
			}
		}
	}
	
	class WanderBehaviour extends PowerConsumingBehaviour {
		public WanderBehaviour(){
			super(WANDER_SB);
		}
		public void action() {
			super.action();
			if(retval != -1)
				return;
			if(!planned.isEmpty()){
				retval = GO_TO_MINERAL;
				return;
			}
			List<Entity> mineralsCloseBy = getCloseBy(EntityGlobals.getDetectionRange(), Mineral.class, false);
			Collections.sort(mineralsCloseBy, new Comparator<Entity>(){
						@Override
						public int compare(Entity arg0, Entity arg1) {
							return Double.compare(dist(arg0), dist(arg1));
						}
			});
			while(!mineralsCloseBy.isEmpty()){
				Mineral m = (Mineral) mineralsCloseBy.get(0);
				/*if(canInteract(m) > 0){
					//planned.add(m);
					retval = FOUND_MINERAL;
					return;
				} else {
					mineralsCloseBy.remove(0);
				}*/
				if(m.getTotal() > 0 && id == 1){
					setMessageFields(m);
					retval = FOUND_MINERAL;
					return;
				} else {
					mineralsCloseBy.remove(0);
				}
			}
			List<Entity> entitiesCloseBy = getCloseBy(EntityGlobals.getCommRange(), Bot.class, false);
			if(!entitiesCloseBy.isEmpty()){
				Bot closest = (Bot) Collections.min(entitiesCloseBy, new Comparator<Entity>(){
						@Override
						public int compare(Entity arg0, Entity arg1) {
							return Double.compare(dist(arg0), dist(arg1));
						}
					}
				);
				if(dist(closest) > dist(0, 0, EntityGlobals.getMaxWidth(), EntityGlobals.getMaxWidth()) / 2){
					moveRandomTo(closest.getX()-getX(), closest.getY()-getY(), maxRange());
				} else if(dist(closest) < 5) {
					moveRandomTo(getX()-closest.getX(), getY()-closest.getY(), maxRange());
				} else {
					moveRandom(maxRange());
				}
			}
			else {
				moveRandom(maxRange());
			}
			
			//moveTo(Entity.getMaxWidth()/2, Entity.getMaxHeight()/2, maxRange());
		}
	}
	class ExecuteBehaviour extends PowerConsumingBehaviour {
		public ExecuteBehaviour(String string) {
			super(string);
		}

		public void action() {
			super.action();
			if(retval != -1)
				return;
			if(planned.isEmpty()){
				retval = NO_PLAN;
				System.out.println("no plan");
				return;
			}
			Mineral m = planned.get(0);
			//System.out.println("distance" + dist(m));
			if(Utils.aproxZero(dist(m))){
				interact(m);
				//System.out.println("can Interact" + canInteract(m));
				if(canInteract(m) <= 0){
					planned.remove(m);
					if(backToBase()){
						retval = OUT_OF_ENERGY;
						return;
					}
				}
			} else {
				moveTo(m.getX(), m.getY(), maxRange());
			}
		}
	}
	
	class ContractBehaviour extends PowerConsumingBehaviour {
		public ContractBehaviour(String string) {
			super(string);
		}

		public void action() {
			super.action();
			if(retval != -1)
				return;
		}
	}
	
	class RechargeBehaviour extends InterruptableBehaviour {
		public RechargeBehaviour(String string) {
			super(string);
		}

		public void action() {
			if(leaveBase(true)){
				retval = RECHARGED;
				return;
			}
			if(Utils.aproxZero(dist(b))){
				atBaseAction(b);
			} else {
				moveTo(b.getX(), b.getY(), maxRange());
			}
			
		}
	}
	class ContractResponseBehaviour extends ContractNetResponder{
		public ContractResponseBehaviour(Agent a, MessageTemplate mt) {
			super(a, mt);
		}
		@Override
		protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
			//System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
			DepositProposalRequest call = null;
			try {
				call = (DepositProposalRequest) getContentManager().extractContent(cfp);
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
			System.out.println("#"+id+": received proposal for " + call.fact.locationx + ", " + call.fact.locationy);
			/*
			int proposal = evaluateAction();
			if (proposal > 2) {
				System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
				ACLMessage propose = cfp.createReply();
				propose.setPerformative(ACLMessage.PROPOSE);
				propose.setContent(String.valueOf(proposal));
				return propose;
			}
			else {
				// We refuse to provide a proposal
				System.out.println("Agent "+getLocalName()+": Refuse");
				throw new RefuseException("evaluation-failed");
			}*/
			ACLMessage proposal = cfp.createReply();
			proposal.setPerformative(ACLMessage.PROPOSE);
			try {
				getContentManager().fillContent(proposal, new DepositProposal(call.fact, Bot.this));
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
			return proposal;
		}
		@Override
		protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
			DepositProposalResponse response = null;
			System.out.println("#" + id + " : my proposal was accepted");
			try {
				response = (DepositProposalResponse) getContentManager().extractContent(accept);
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
			planned.add(Mineral.getMineral(response.fact.locationx, response.fact.locationy));
			//retval = NEW_CONTRACT;
			ACLMessage inform = accept.createReply();
			inform.setPerformative(ACLMessage.INFORM);
			return inform;
			/*System.out.println("Agent "+getLocalName()+": Proposal accepted");
			if (performAction()) {
				System.out.println("Agent "+getLocalName()+": Action successfully performed");
				ACLMessage inform = accept.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				return inform;
			}
			else {
				System.out.println("Agent "+getLocalName()+": Action execution failed");
				throw new FailureException("unexpected-error");
			}*/
		}

		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
			System.out.println("Agent "+getLocalName()+": Proposal rejected");
		}
	}
	
	class ContractInitBehaviour extends ContractNetInitiator{

		public ContractInitBehaviour(Agent a, ACLMessage cfp) {
			super(a, cfp);
		}
		protected Vector prepareCfps(ACLMessage cfp){
			this.restart();
			System.out.println("Preparing cfps");
			return super.prepareCfps(cfp);
		}
		protected void sendInitiations(Vector initiations){
			System.out.println("sending initiations");
			super.sendInitiations(initiations);
		}
		protected void handlePropose(ACLMessage propose, Vector v) {
			System.out.println("Agent "+propose.getSender().getName()+" proposed ");
		}
		
		protected void handleRefuse(ACLMessage refuse) {
			System.out.println("Agent "+refuse.getSender().getName()+" refused");
		}
		
		protected void handleFailure(ACLMessage failure) {
			if (failure.getSender().equals(myAgent.getAMS())) {
				// FAILURE notification from the JADE runtime: the receiver
				// does not exist
				System.out.println("Responder does not exist");
			}
			else {
				System.out.println("Agent "+failure.getSender().getName()+" failed");
			}
		}
		protected void handleAllResponses(Vector responses, Vector acceptances) {
			
			List<Map.Entry<Double, ACLMessage>> proposalsScan = new ArrayList<>();
			List<Map.Entry<Double, ACLMessage>> proposalsExtract = new ArrayList<>();
			List<Map.Entry<Double, ACLMessage>> proposalsTransport = new ArrayList<>();
			Enumeration e = responses.elements();
			while (e.hasMoreElements()) {
				ACLMessage msg = (ACLMessage) e.nextElement();
				if (msg.getPerformative() == ACLMessage.PROPOSE) {
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
					acceptances.addElement(reply);
					DepositProposal prop = null;
					try {
						prop = (DepositProposal) getContentManager().extractContent(msg);
					} catch (CodecException | OntologyException except) {
						except.printStackTrace();
					}
					List<Map.Entry<Double, ACLMessage>> toadd = null;
					switch(prop.type){
					case SCAN:
						toadd = proposalsScan;
						break;
					case EXTRACT:
						toadd = proposalsExtract;
						break;
					case TRANSPORT:
						toadd = proposalsTransport;
						break;
					}
					DepositProposalResponse r = new DepositProposalResponse(prop);
					try {
						getContentManager().fillContent(reply, r);
					} catch (CodecException | OntologyException except) {
						except.printStackTrace();
					}
					toadd.add(new AbstractMap.SimpleEntry<Double, ACLMessage>(prop.cost, reply));
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
				}
			}
			Comparator<Map.Entry<Double, ACLMessage>> c = new Comparator<Map.Entry<Double, ACLMessage>>(){
				@Override
				public int compare(Entry<Double, ACLMessage> arg0, Entry<Double, ACLMessage> arg1) {
					return arg0.getKey().compareTo(arg1.getKey());
				}
				
			};
			Collections.sort(proposalsScan, c);
			Collections.sort(proposalsExtract, c);		
			Collections.sort(proposalsTransport, c);
			List<Map.Entry<Double, ACLMessage>> accepted = new ArrayList<>();
			accepted.addAll(proposalsScan);
			accepted.addAll(proposalsExtract);
			accepted.addAll(proposalsTransport);
			for(Map.Entry<Double, ACLMessage> elem : accepted){
				elem.getValue().setPerformative(ACLMessage.ACCEPT_PROPOSAL);
				System.out.println("#" + id +" : accepting proposal, sending message");
			}
		}
		protected void handleInform(ACLMessage inform) {
			System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
			
			//retval = FINISHED_CONTRACT; 
		}
	}
	
	private int evaluateAction() {
		return (int) (Math.random() * 10);
	}

	private boolean performAction() {
		// Simulate action execution by generating a random number
		return (Math.random() > 0.2);
	}
	
	private Behaviour respondCFP(){
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP));
		return new ContractResponseBehaviour(this, template);
	}
	
	class FinalBehaviour extends InterruptableBehaviour {
		public FinalBehaviour(String string) {
			super(string);
		}

		public void action() {
			moveTo(b.getX(), b.getY(), maxRange());
		}
	}
	
	class RetParallelBehaviour extends ParallelBehaviour{
		public RetParallelBehaviour(int whenAny) {
			super(whenAny);
		}

		@Override
		public int onEnd() {
			Collection c = this.getTerminatedChildren();
			Iterator it = c.iterator();
			int ret = 0;
			while(it.hasNext()){
				Behaviour b = (Behaviour) it.next();
				if(b instanceof RetraceableBehaviour){
					ret = ((RetraceableBehaviour)b).getPreviousReturnValue();
				} 
				else ret = b.onEnd();
				//System.out.println("#" + id + ": " + b.getBehaviourName() + " returned " + ret);
			}
			return ret;
		}
	}
	
	private Behaviour wanderBehaviour(){
		return new WanderBehaviour();
	}
	
	private Behaviour rechargeBehaviour(){
		return new RechargeBehaviour(RECHARGE_B);
	}
	
	private Behaviour executeBehaviour(){
		return new ExecuteBehaviour(EXECUTE_B);
	}
	private List<AID> agentsWithinRange(){
		return this.getCloseBy(EntityGlobals.getCommRange(), Bot.class, true).stream().map(e -> ((Bot)e).getAID()).collect(Collectors.toList());
	}
	
	private void setMessageFields(Mineral m){
		mycfp.clearAllReceiver();
		List<AID> agents = agentsWithinRange();
  		for (AID a : agents) {
  			mycfp.addReceiver(a);
  		}
  		mycfp.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
  		
  		DepositProposalRequest r = new DepositProposalRequest(m, 0);
  		try {
			getContentManager().fillContent(mycfp, r);
		} catch (CodecException | OntologyException e) {
			e.printStackTrace();
		}
  	}
	
	private Behaviour contractInitBehaviour(){
		
		return new ContractInitBehaviour(this, mycfp);
	}
	
	protected void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("BOT");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		codec = new SLCodec();
		onto = MarsOntology.getInstance();
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(onto);
		
		mycfp = new ACLMessage(ACLMessage.CFP);
		mycfp.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		mycfp.setLanguage(codec.getName());
		mycfp.setOntology(onto.getName());
		
		
		//WanderBehaviour b = new WanderBehaviour(this);
		fsm = new FSMBehaviour(this);
		
		fsm.registerFirstState(wanderBehaviour(), WANDER_B);
		fsm.registerState(rechargeBehaviour(), RECHARGE_B);
		fsm.registerState(executeBehaviour(), EXECUTE_B);
		fsm.registerLastState(new FinalBehaviour("Final"), "Final");
		fsm.registerState(new OneShotBehaviour(){
			@Override
			public void action() {
				//fsm.deregisterState(INIT_CONTRACT_NET_B);
				fsm.registerState(contractInitBehaviour(), INIT_CONTRACT_NET_B);
				//fsm.registerDefaultTransition(START_INIT_CONTRACT_NET_B, INIT_CONTRACT_NET_B);
				//fsm.registerDefaultTransition(INIT_CONTRACT_NET_B, WANDER_B);
			}
		}, START_INIT_CONTRACT_NET_B);
		
		
		//fsm.registerState(contractInitBehaviour(), INIT_CONTRACT_NET_B);
		fsm.registerDefaultTransition(START_INIT_CONTRACT_NET_B, INIT_CONTRACT_NET_B);
		fsm.registerDefaultTransition(INIT_CONTRACT_NET_B, WANDER_B);
		
		//fsm.registerState(contractInitBehaviour(), INIT_CONTRACT_NET_B);
		
		registerTransition(WANDER_B, RECHARGE_B, OUT_OF_ENERGY);
		registerTransition(EXECUTE_B, RECHARGE_B, OUT_OF_ENERGY);
		registerTransition(RECHARGE_B, WANDER_B, RECHARGED);
		
		fsm.registerTransition(WANDER_B, START_INIT_CONTRACT_NET_B, FOUND_MINERAL);
		//fsm.registerTransition(WANDER_B, INIT_CONTRACT_NET_B, FOUND_MINERAL);
		registerTransition(WANDER_B, EXECUTE_B, GO_TO_MINERAL);
		//fsm.registerDefaultTransition(INIT_CONTRACT_NET_B, WANDER_B, new String[]{INIT_CONTRACT_NET_B});
		
		registerTransition(WANDER_B, EXECUTE_B, NEW_CONTRACT);
		registerTransition(EXECUTE_B, WANDER_B, NO_PLAN);
		addBehaviour(fsm);
		/*FSMBehaviour fsm2 = new FSMBehaviour(this){
			@Override
			public int onEnd(){
				System.out.println("ending");
				return super.onEnd();
			}
		};
		fsm2.registerFirstState(respondCFP(), "Respond");
		fsm2.registerDefaultTransition("Respond", "Respond");*/
		//addBehaviour(fsm2);
		addBehaviour(respondCFP());
	}   
	private void registerTransition(String src, String dst, int code){
		fsm.registerTransition(src, dst, code);
	}
	protected boolean backToBase(){
		return dist(b) > energy - maxRange();
	}

	protected int interact(Mineral m){
		int ret = canInteract(m);
		if(ret == 0)
			return 0;
		this.energy-=EntityGlobals.getPassiveDischarge();
		if (energy < 0)
			energy = 0;
		return ret;
	}

	protected abstract int canInteract(Mineral min);

	protected void takeDown() {
		try {
			DFService.deregister(this);  
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onMove(double dist) {
		energy -= dist;
		if (energy < 0)
			energy = 0;
	}

	@Override
	public double maxRange() {
		return Math.max(0,Math.min(energy, getMaxspeed()));
	}
	
	protected void recharge(){
		energy +=  EntityGlobals.getRechargeRate();
		energy = energy >  EntityGlobals.getMaxEnergy() ?  EntityGlobals.getMaxEnergy() : energy;
	}

	public double getEnergy() {
		return energy;
	}
	
	protected boolean leaveBase(boolean recharging){
		return Utils.aproxSame(energy, EntityGlobals.getMaxEnergy());
	}
	
	protected void atBaseAction(Base b){
		recharge();
	}
}

