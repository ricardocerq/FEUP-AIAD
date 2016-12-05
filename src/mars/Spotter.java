package mars;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Spotter extends Bot{

	public Spotter(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x,
			double y, Base b) {
		super(context, cs, grid, maxspeed, x, y, b);
	}

	@Override
	public int canInteract(Mineral min) {
		return min.getScannable();
	}

	@Override
	public int interact(Mineral m) {
		return 0;
	}

}
