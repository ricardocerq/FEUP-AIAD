package mars;

import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import sajas.domain.DFService;
import jade.domain.FIPAException;

// classe do agente
public class Bot extends Entity {
	private static double maxEnergy;
	
	public static double getMaxEnergy() {
		return maxEnergy;
	}

	public static void setMaxEnergy(double maxEnergy) {
		Bot.maxEnergy = maxEnergy;
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
	
	class WanderBehaviour extends CyclicBehaviour {
		public WanderBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			moveRandom(Math.max(0,Math.min(energy, getMaxspeed())));
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

		WanderBehaviour b = new WanderBehaviour(this);
		addBehaviour(b);
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

}

