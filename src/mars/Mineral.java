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
	
	private int deposit;
	private int scanned;
	private int extracted;
	
	public Mineral(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y){
		super(context, cs, grid, 0, x, y);
		deposit = Utils.r.nextInt(MAX-MIN)+MIN;
		scanned = 0;
		extracted = 0;
	}
	
	public static int getNewMineralValue(){
		return Utils.r.nextInt(MAX-MIN)+MIN;
	}
	
	public float depositAmount() {
		return ((float)(this.deposit))/(MAX);
	}
	
	public float scannedAmount() {
		return ((float)(this.scanned))/(MAX);
	}
	
	public float extractedAmount() {
		return ((float)(this.extracted))/(MAX);
	}
	
	@Override
	protected void onMove(double dist) {
	}
	
	@Override
	public double maxRange() {
		return 0;
	}
	
	public int getScannable() {
		return deposit;
	}
	
	public int getScanned() {
		return scanned;
	}
	
	public int getExtracted() {
		return extracted;
	}
	
	public int scan(int amount){
		int actual = Math.max(0, Math.min(amount, deposit));
		deposit -= actual;
		scanned += actual;
		return actual;
	}
	
	public int extract(int amount){
		int actual = Math.max(0, Math.min(amount, scanned));
		scanned -= actual;
		extracted += actual;
		return actual;
	}
	
	public int take(int amount){
		int actual = Math.max(0, Math.min(amount, extracted));
		extracted -= actual;
		return actual;
	}
}