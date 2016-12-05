package mars;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Base extends Entity {
	private double gathered;

	public Base(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y){
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
	
	public double getGathered() {
		return gathered;
	}

	public void setGathered(double gathered) {
		this.gathered = gathered;
	}
}
