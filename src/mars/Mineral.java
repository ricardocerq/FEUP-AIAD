package mars;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;
import repast.simphony.valueLayer.GridValueLayer;

public class Mineral {
	private int countdown;   // coundown timer for mineral to re-grow
	private boolean alive;   // boolean for mineral alive / dead
	
	private static final int ALIVE = 1;
	private static final int DEAD = 0;
	private static final int MAX;
	private static final int MIN;
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
	private GridValueLayer depositField;
	private GridValueLayer extractedField;
	
	public Mineral(GridValueLayer depositField, GridValueLayer extractedField, int x1, int y1){
		deposit = Utils.r.nextDouble()*(MAX-MIN)+MIN;
		mined = 0;
		depositField.set(deposit, x1,y1);
		this.depositField = depositField;
		this.extractedField = extractedField;
	}
	
	public static int getNewMineralValue(){
		return Utils.r.nextInt(MAX-MIN)+MIN;
	}
}