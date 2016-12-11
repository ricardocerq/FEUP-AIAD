package mars;

import onto.DepositFact;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Spotter extends Bot{

	public Spotter(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x,
			double y, Base b) {
		super(context, cs, grid, maxspeed, x, y, b);
	}

	@Override
	protected int canInteract(Mineral min) {
		return min.getScannable();
	}

	@Override
	protected int interact(Mineral m) {
		if(super.interact(m) == 0)
			return 0;
		return m.scan(EntityGlobals.getScanSpeed());
	}

	@Override
	protected int canInteract(DepositFact min) {
		return min.scanCompleted? 0 : Mineral.averageAmount() ;
	}

	@Override
	protected int getInteractionSpeed() {
		return EntityGlobals.getScanSpeed();
	}

}
