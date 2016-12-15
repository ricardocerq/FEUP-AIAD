package mars;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Base extends Entity {
	private int gathered;

	public Base(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y) {
		super(context, cs, grid, 0, x, y);
		gathered = 0;
	}

	@Override
	protected void onMove(double dist) {
	}

	@Override
	public double maxRange() {
		return 0;
	}

	public int getGathered() {
		return gathered;
	}
	
	public double getPercentageGathered() {
		return ((double)gathered)/Mineral.getGlobalTotal();
	}

	public void setGathered(int gathered) {
		this.gathered = gathered;
	}

	public void addMineral(int amount) {
		gathered += amount;
	}
	@ScheduledMethod(start = 1, interval = 1)
	public void stop(){
		if(gathered == Mineral.getGlobalTotal()){
			RunEnvironment.getInstance().endRun();
		}
	}
}
