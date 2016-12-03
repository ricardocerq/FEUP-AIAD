package mars;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Mineral extends Entity{
	private int countdown;   // coundown timer for mineral to re-grow
	private boolean alive;   // boolean for mineral alive / dead
	
	private static final int ALIVE = 1;
	private static final int DEAD = 0;
	private static final int MAX;
	private static final int MIN;
	public float mineralAmount(){
		return ((float)(this.deposit - MIN))/(MAX-MIN);
	}
	public static int getMAX() {
		return MAX;
	}
	public static int getMIN() {
		return MIN;
	}
	
	static {
		Parameters p = RunEnvironment.getInstance().getParameters();
		MAX = (Integer)p.getValue("mineralmaxvalue");
		MIN = (Integer)p.getValue("mineralminvalue");
	}
	
	private double deposit;
	private double mined;
	
	public Mineral(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y){
		super(context, cs, grid, 0, x, y);
		deposit = Utils.r.nextDouble()*(MAX-MIN)+MIN;
		mined = 0;
	}
	
	public static int getNewMineralValue(){
		return Utils.r.nextInt(MAX-MIN)+MIN;
	}
}