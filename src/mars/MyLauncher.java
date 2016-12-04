package mars;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;
import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.valueLayer.GridValueLayer;
import sajas.core.Agent;
import sajas.core.Runtime;
import sajas.sim.repasts.RepastSLauncher;
import sajas.wrapper.ContainerController;

public class MyLauncher extends RepastSLauncher {

	private ContainerController mainContainer;

	@Override
	public String getName() {
		return "SAJaS Project";
	}

	@Override
	protected void launchJADE() {
		Runtime rt = Runtime.instance();
		Profile p1 = new ProfileImpl();
		mainContainer = rt.createMainContainer(p1);
		launchAgents();
	}
	
	private ArrayList<Agent> agents = new ArrayList<>();
	
	private void launchAgents() {

		try {
			int i = 0;
			for(Agent a : agents){
				mainContainer.acceptNewAgent("Bot " + i++ , a).start();
			}
			
			// ...

		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Context build(Context<Object> context) {
		agents.clear();
		Entity.clearEntities();
		Parameters p = RunEnvironment.getInstance().getParameters();
		int xdim = (Integer)p.getValue("xdim");   // The x dimension of the physical space
		int ydim = (Integer)p.getValue("ydim");   // The y dimension of the physical space
		
		int numBots = (Integer)p.getValue("initialnumberofbots");
		int numBases = (Integer)p.getValue("numberofbases");
		int maxdist = (Integer)p.getValue("maxinitialbotdistance");
		
		int numminerals = Math.min((Integer)p.getValue("initialnumberofminerals"), xdim*ydim);
		
		double maxspeed = (Double)p.getValue("maxspeed");
		
		double maxenergy = (Double)p.getValue("maxenergy");
		
		Bot.setMaxEnergy(maxenergy);
		
		double rechargeRate = (Double)p.getValue("rechargerate");
		
		Bot.setRechargeRate(rechargeRate);
		
		double commRange = (Double)p.getValue("commrange");
		
		Bot.setCommRange(commRange);
		
		
		Entity.setMaxWidth(xdim);
		Entity.setMaxHeight(ydim);
		
		
		Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid("Simple Grid", context,
				new GridBuilderParameters<Object>(new repast.simphony.space.grid.WrapAroundBorders(),
						new SimpleGridAdder<Object>(), true, xdim, ydim));

		ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace("Continuous Space", context, new SimpleCartesianAdder<Object>(),
					new repast.simphony.space.continuous.WrapAroundBorders(), xdim, ydim, 1);

		GridValueLayer depositField = new GridValueLayer("Mineral Field", true, 
				new repast.simphony.space.grid.WrapAroundBorders(),xdim,ydim);

		context.addValueLayer(depositField);
		
		
		for (int j = 0; j < numBases; j++) {
			
			double x1 = Utils.r.nextInt(xdim - 2*maxdist - 2) + maxdist + 1 +.5;
			double y1 = Utils.r.nextInt(ydim - 2*maxdist - 2)+ maxdist + 1+.5;
			
			if(find(grid, (int)x1, (int)y1, Base.class).isEmpty()) {
				
				Base b = new Base(context, space, grid, x1, y1);
				
				for (int i = 0; i < numBots; i++) {
					         
					float r = Utils.r.nextFloat()*(maxdist-1)+1;
					double ang = ((double)i)/numBots*2*Math.PI;
					double x2 = x1 + r*(double)Math.cos(ang);
					double y2 = y1 + r*(double)Math.sin(ang);
					Bot bot = new Bot(context, space, grid, maxspeed, x2, y2, b);   
					agents.add(bot);
				}
			} else j--;
		}
		
		for (int i = 0; i < numminerals; i++) {
			int x1 = Utils.r.nextInt(xdim);
			int y1 = Utils.r.nextInt(ydim);
			if(find(grid, (int)x1, (int)y1, Mineral.class).isEmpty()) {
				Mineral m = new Mineral(context, space, grid, x1 + .5, y1 + .5);
			}
			else i--;
		}
		
		if (RunEnvironment.getInstance().isBatch()) {
			double endAt = (Double)p.getValue("runlength");     
			RunEnvironment.getInstance().endAt(endAt);
		}
		return super.build(context);
	}
	public List<Object> find(Grid<Object> grid, int x, int y, Class c){
		Iterable<Object> it = grid.getObjectsAt(x,y);
		List<Object> list = new LinkedList<>();
		for(Object obj: it){
			if(obj.getClass().equals(c))
				list.add(obj);
		}
		return list;
	}
}
