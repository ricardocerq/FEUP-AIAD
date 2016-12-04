package mars;

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
public class Bot extends Entity {
	private static double maxEnergy;
	private static double rechargeRate;
	private static double commRange;
	
	public static double getMaxEnergy() {
		return maxEnergy;
	}

	public static void setMaxEnergy(double maxEnergy) {
		Bot.maxEnergy = maxEnergy;
	}
	
	public static double getRechargeRate() {
		return rechargeRate;
	}

	public static void setRechargeRate(double rechargeRate) {
		Bot.rechargeRate = rechargeRate;
	}
	
	public static double getCommRange() {
		return commRange;
	}

	public static void setCommRange(double commRange) {
		Bot.commRange = commRange;
	}
	
	private double energy;       // The energy level of the agent
	private double heading;      // The heading in degrees of the agent
	private Base b;
	private static int nextid = 1;
	private int id;
	public Bot(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x, double y, Base b){
		super(context, cs, grid, maxspeed, x, y);
		this.b = b;
		id = nextid++;
		System.out.println("Bot " + id + " Created");
		energy = Bot.maxEnergy;
		
	}
	private static final int OUT_OF_ENERGY = 1;
	private static final int RECHARGED = 2;
	
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
	
	class WanderBehaviour extends InterruptableBehaviour {
		public void action() {
			//System.out.println("WanderBehaviour executing");
			System.out.println(energy);
			if(dist(b) > energy - maxRange()){
				retval = OUT_OF_ENERGY;
				return;
			}
			List<Entity> closeBy = getCloseBy(commRange, Bot.class);
			if(!closeBy.isEmpty()){
				Bot closest = (Bot) Collections.min(closeBy, new Comparator<Entity>(){
						@Override
						public int compare(Entity arg0, Entity arg1) {
							return Double.compare(dist(arg0), dist(arg1));
						}
					}
				);
				System.out.println(closest.getX() + " "  + closest.getY());
				System.out.println("dist: " + dist(closest));
				if(dist(closest) > 100){
					moveRandomTo(closest.getX()-getX(), closest.getY()-getY(), maxRange());
				} else if(dist(closest) < 10) {
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
	
	class RechargeBehaviour extends InterruptableBehaviour {
		public void action() {
			System.out.println("RechargeBehaviour executing");
			
			if(Utils.aproxSame(energy, maxEnergy)){
				System.out.println("recharge finished");
				retval = RECHARGED;
				return;
			}
			System.out.print("energy " + energy); System.out.println("  dist " + dist(b));
			if(Utils.aproxZero(dist(b))){
				recharge();
			} else {
				System.out.println("moving back");
				moveTo(b.getX(), b.getY(), maxRange());
			}
			
		}
	}
	
	class FinalBehaviour extends InterruptableBehaviour {
		public void action() {
			System.out.println("FinalBehaviour executing");
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
		bb.registerLastState(new FinalBehaviour(), "Final");
		bb.registerTransition("Wander", "Recharge", OUT_OF_ENERGY);
		bb.registerTransition("Recharge", "Wander", RECHARGED);
		addBehaviour(bb);
	}   
	
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
	
	public void recharge(){
		System.out.println("Recharging " + energy + " to " + energy + rechargeRate);
		energy += rechargeRate;
		energy = energy > maxEnergy ? maxEnergy: energy;
	}
}

