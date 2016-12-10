package mars;

import onto.DepositFact;
import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Transporter extends Bot {
	
	private int carrying;
	
	public Transporter(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed,
			double x, double y, Base b) {
		super(context, cs, grid, maxspeed, x, y, b);
		carrying = 0;
	}
	
	@Override
	protected int canInteract(Mineral min) {
		return min.getExtracted();
	}

	@Override
	protected int interact(Mineral m) {
		if(super.interact(m) == 0)
			return 0;
		int amount = m.gather(Math.max(Math.min(EntityGlobals.getGatherSpeed(),EntityGlobals.getMaxCapacity() - carrying), 0));
		carrying += amount;
		return amount;
	}

	@Override
	protected boolean backToBase() {
		return super.backToBase() || EntityGlobals.getMaxCapacity() < carrying + EntityGlobals.getGatherSpeed();
	}

	@Override
	protected boolean leaveBase(boolean recharging) {
		return super.leaveBase(recharging) && carrying == 0; 
	}
	
	@Override
	protected void atBaseAction(Base base){
		super.atBaseAction(base);
		int amount = Math.min(carrying, EntityGlobals.getUnloadSpeed());
		carrying -= amount;
		base.addMineral(amount);
	}

	@Override
	protected int canInteract(DepositFact min) {
		return min.amountExtracted;
	}
	
	@Override
	public boolean shouldLeave(Mineral m){
		return super.shouldLeave(m) || carrying + EntityGlobals.getGatherSpeed() > EntityGlobals.getMaxCapacity();
	}
	
	public int getCarrying() {
		return carrying;
	}
}
