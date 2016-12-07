package mars;

import onto.DepositFact;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Producer extends Bot{

	public Producer(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x,
			double y, Base b) {
		super(context, cs, grid, maxspeed, x, y, b);
	}

	@Override
	protected int canInteract(Mineral min) {
		return min.getScanned();
	}

	@Override
	protected int interact(Mineral m) {
		if(super.interact(m) == 0)
			return 0;
		return m.extract(EntityGlobals.getExtractionSpeed());
	}

	@Override
	protected int canInteract(DepositFact min) {
		return min.amountScanned;
	}

}
