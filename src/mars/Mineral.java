package mars;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Mineral extends Entity{
	private static int globalTotal = 0;
	
	private int deposit;
	private int scanned;
	private int extracted;
	
	public Mineral(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double x, double y){
		super(context, cs, grid, 0, x, y);
		deposit = Utils.r.nextInt(EntityGlobals.getMaxMineralValue()-EntityGlobals.getMinMineralValue())+EntityGlobals.getMinMineralValue();
		globalTotal += deposit;
		scanned = 0;
		extracted = 0;
	}
	
	public static int getNewMineralValue(){
		return Utils.r.nextInt(EntityGlobals.getMaxMineralValue()-EntityGlobals.getMinMineralValue())+EntityGlobals.getMinMineralValue();
	}
	
	public float depositAmount() {
		return ((float)(this.deposit))/(EntityGlobals.getMaxMineralValue());
	}
	
	public float scannedAmount() {
		return ((float)(this.scanned))/(EntityGlobals.getMaxMineralValue());
	}
	
	public float extractedAmount() {
		return ((float)(this.extracted))/(EntityGlobals.getMaxMineralValue());
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
	
	public int gather(int amount){
		int actual = Math.max(0, Math.min(amount, extracted));
		extracted -= actual;
		return actual;
	}
	
	public int getTotal(){
		return this.getScannable()+this.getScanned()+this.getExtracted();
	}
	
	public static Mineral getMineral(double x, double y){
		for(Entity e: entities){
			if(e instanceof Mineral && Utils.aproxZero(Entity.dist(x, y, e.getX(), e.getY()))){
				return (Mineral)e;
			}
		}
		return null;
	}
	public static int averageAmount(){
		return EntityGlobals.getMinMineralValue() + (EntityGlobals.getMaxMineralValue()-EntityGlobals.getMinMineralValue())/2;
	}

	public static int getGlobalTotal() {
		return globalTotal;
	}

	public static void setGlobalTotal(int globalTotal) {
		Mineral.globalTotal = globalTotal;
	}
}