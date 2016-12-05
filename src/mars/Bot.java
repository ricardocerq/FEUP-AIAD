package mars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import sajas.core.behaviours.FSMBehaviour;
import sajas.core.behaviours.SimpleBehaviour;
import sajas.domain.DFService;

// classe do agente
public abstract class Bot extends Entity {
	
	
	private double energy;       // The energy level of the agent
	private double heading;      // The heading in degrees of the agent
	private Base b;
	private List<Mineral> planned = new ArrayList<>();
	private static int nextid = 1;
	private int id;
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
	
	abstract class InterruptableBehaviour extends SimpleBehaviour {
		int retval = -1;
		@Override
		public int onEnd() {
			int ret = retval;
			retval = -1;
			return ret;
		}
		@Override
		public boolean done() {
			boolean ret = retval != -1;
			return ret;
		}
	}
	
	class PowerConsumingBehaviour extends InterruptableBehaviour {
		public void action() {
			if(dist(b) > energy - maxRange()){
				retval = OUT_OF_ENERGY;
				return;
			}
		}
	}
	
	class WanderBehaviour extends PowerConsumingBehaviour {
		public void action() {
			super.action();
			if(retval != -1)
				return;
			if(!planned.isEmpty()){
				retval = FOUND_MINERAL;
				return;
			}
			List<Entity> mineralsCloseBy = getCloseBy(EntityGlobals.getDetectionRange(), Mineral.class);
			Collections.sort(mineralsCloseBy, new Comparator<Entity>(){
						@Override
						public int compare(Entity arg0, Entity arg1) {
							return Double.compare(dist(arg0), dist(arg1));
						}
			});
			while(!mineralsCloseBy.isEmpty()){
				Mineral m = (Mineral) mineralsCloseBy.get(0);
				if(canInteract(m) > 0){
					planned.add(m);
					retval = FOUND_MINERAL;
					return;
				} else {
					mineralsCloseBy.remove(0);
				}
			}
			List<Entity> entitiesCloseBy = getCloseBy(EntityGlobals.getCommRange(), Bot.class);
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
		public void action() {
			super.action();
			if(retval != -1)
				return;
			if(planned.isEmpty()){
				retval = NO_PLAN;
				return;
			}
			Mineral m = planned.get(0);
			if(Utils.aproxZero(dist(m))){
				interact(m);
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
		public void action() {
			super.action();
			if(retval != -1)
				return;
			
			
			
		}
	}
	
	class RechargeBehaviour extends InterruptableBehaviour {
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
	
	class FinalBehaviour extends InterruptableBehaviour {
		public void action() {
			moveTo(b.getX(), b.getY(), maxRange());
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

		//WanderBehaviour b = new WanderBehaviour(this);
		FSMBehaviour bb = new FSMBehaviour(this);
		
		bb.registerFirstState(new WanderBehaviour(), "Wander");
		bb.registerState(new RechargeBehaviour(), "Recharge");
		bb.registerState(new ExecuteBehaviour(), "Execute");
		bb.registerLastState(new FinalBehaviour(), "Final");
		bb.registerTransition("Wander", "Recharge", OUT_OF_ENERGY);
		bb.registerTransition("Execute", "Recharge", OUT_OF_ENERGY);
		bb.registerTransition("Recharge", "Wander", RECHARGED);
		bb.registerTransition("Wander", "Execute", FOUND_MINERAL);
		bb.registerTransition("Execute", "Wander", NO_PLAN);
		addBehaviour(bb);
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

