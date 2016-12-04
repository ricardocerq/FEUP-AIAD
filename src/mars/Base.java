package mars;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Base extends Entity {
	public Base(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y){
		super(context, cs, grid, 0, x, y);
	}

	@Override
	protected void onMove(double dist) {
	}
}
