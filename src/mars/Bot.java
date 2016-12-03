package mars;

import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import sajas.domain.DFService;
import jade.domain.FIPAException;

// classe do agente
public class Bot extends Agent {
	private double energy;       // The energy level of the agent
	private double heading;      // The heading in degrees of the agent
	private Base b;
	private static int nextid = 1;
	private int id;
	public Bot(Base b){
		this.b = b;
		id = nextid++;
		System.out.println("Bot " + id + " Created");
	}
	
	// classe do behaviour
	class BotBehaviour extends CyclicBehaviour {
		private int n = 0;

		// construtor do behaviour
		public BotBehaviour(Agent a) {
			super(a);
		}

		// método action
		public void action() {
			move();
		}

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

		BotBehaviour b = new BotBehaviour(this);
		addBehaviour(b);
	}   // fim do metodo setup

	// método takeDown
	protected void takeDown() {
		// retira registo no DF
		try {
			DFService.deregister(this);  
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}

	// Move the agent
	public void move() {
		System.out.println("Bot " + id + " moving");
		// The agent is aware of its location in the continuous space and
		// which mineral patch it is on

		// Get the context in which the agent is residing
		Context context = ContextUtils.getContext(this);

		// Get the patch grid from the context
		Grid patch = (Grid) context.getProjection("Simple Grid");

		// Get the continuous space from the context
		ContinuousSpace space = (ContinuousSpace) context.getProjection("Continuous Space");

		NdPoint point = space.getLocation(this);  // Get the agent's point coordinate from the space

		double x = point.getX();			// The x coordinate on the 2D continuous space
		double y = point.getY();      // The y coordinate on the 2D continuous space

		// Randomly change the current heading plus or minus 50 degrees
		double sgn = Math.random() - 0.5;       // a value between -0.5 and 0.5
		if (sgn > 0)
			heading = heading + Math.random()*50;
		else
			heading = heading - Math.random()*50;

		// Move the agent on the space by one unit according to its new heading
		space.moveByVector(this, .1, Math.toRadians(heading),0,0);

		// Move the agent to its new patch (note the patch may not actually change)
		patch.moveTo(this, (int)x, (int)y);	
	}

}

