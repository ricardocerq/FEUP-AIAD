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
		Parameters p = RunEnvironment.getInstance().getParameters();
		int xdim = (Integer)p.getValue("xdim");   // The x dimension of the physical space
		int ydim = (Integer)p.getValue("ydim");   // The y dimension of the physical space

		Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid("Simple Grid", context,
				new GridBuilderParameters<Object>(new repast.simphony.space.grid.WrapAroundBorders(),
						new SimpleGridAdder<Object>(), true, xdim, ydim));

		ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
				.createContinuousSpace("Continuous Space", context, new SimpleCartesianAdder<Object>(),
					new repast.simphony.space.continuous.WrapAroundBorders(), xdim, ydim, 1);

		GridValueLayer depositField = new GridValueLayer("Mineral Field", true, 
				new repast.simphony.space.grid.WrapAroundBorders(),xdim,ydim);

		context.addValueLayer(depositField);
		
		GridValueLayer extractedField = new GridValueLayer("Extracted Field", true, 
				new repast.simphony.space.grid.WrapAroundBorders(),xdim,ydim);

		context.addValueLayer(extractedField);
		
		int numBots = (Integer)p.getValue("initialnumberofbots");
		int numBases = (Integer)p.getValue("numberofbases");
		int maxdist = (Integer)p.getValue("maxinitialbotdistance");
		for (int j = 0; j < numBases; j++) {
			float x1 = Utils.r.nextInt(xdim)+.5f;
			float y1 = Utils.r.nextInt(ydim)+.5f;
			
			if(find(grid, (int)x1, (int)y1, Base.class).isEmpty()){
				Base b = new Base();
				context.add(b);
				space.moveTo(b, x1, y1, 1);
				//grid.moveTo(b, (int)x1, (int)y1);
				
				for (int i = 0; i < numBots; i++) {
					Bot bot = new Bot(b);            
					context.add(bot);
					float r = Utils.r.nextFloat()*(maxdist-1)+1;
					r = 1;
					double ang = ((float)i)/numBots*2*Math.PI;
					float x2 = x1 + r*(float)Math.cos(ang);
					float y2 = y1 + r*(float)Math.sin(ang);
					agents.add(bot);
					space.moveTo(bot, x2, y2, 1);
					//grid.moveTo(bot, space.get, (int)y2);
				}
			} else j--;
		}

		int numminerals = Math.min((Integer)p.getValue("initialnumberofminerals"), xdim*ydim);
		for (int i = 0; i < numminerals; i++) {
			int x1 = Utils.r.nextInt(xdim);
			int y1 = Utils.r.nextInt(ydim);
			if(depositField.get(x1,y1) == 0){
				Mineral m = new Mineral(depositField, extractedField, x1, y1);
				context.add(m);
				space.moveTo(m, x1+.5,y1+.5, 1);
				//grid.moveTo(m, (int)(x1+.5),(int)(y1+.5));
				
			}
			else i--;
		}
		for (Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}
		if (RunEnvironment.getInstance().isBatch()){

			double endAt = (Double)p.getValue("runlength");     
			RunEnvironment.getInstance().endAt(endAt);
		}
		return super.build(context);
	}
	public <T> List<Class<T>> find(Grid<Object> grid, int x, int y, Class<T> c){
		Iterable<Object> it = grid.getObjectsAt(x,y);
		List<Class<T>> list = new LinkedList<>();
		for(Object obj: it){
			if(obj.getClass().equals(c))
				list.add((Class<T>)obj);
		}
		return list;
	}
}
