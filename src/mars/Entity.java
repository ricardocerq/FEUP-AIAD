package mars;


import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Entity {
	
	private static int maxWidth;
	private static int maxHeight;
	private static boolean wrapAround = true;
	private double x;
	private double y;
	private double maxspeed;
	private double targetx;
	private double targety;
	private ContinuousSpace<Object> cs;
	private Grid<Object> grid;
	
	public static int getMaxWidth() {
		return maxWidth;
	}
	
	public static void setMaxWidth(int maxWidth) {
		Entity.maxWidth = maxWidth;
	}
	
	public static int getMaxHeight() {
		return maxHeight;
	}
	
	public static void setMaxHeight(int maxHeight) {
		Entity.maxHeight = maxHeight;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getMaxspeed() {
		return maxspeed;
	}
	
	public void setMaxspeed(double maxspeed) {
		this.maxspeed = maxspeed;
	}
	
	public double getTargetx() {
		return targetx;
	}
	
	public void setTargetx(double targetx) {
		this.targetx = targetx;
	}
	
	public double getTargety() {
		return targety;
	}
	
	public void setTargety(double targety) {
		this.targety = targety;
	}
	
	public void commitMove(){
		doWrapAround();
		x = targetx;
		y = targety;
		targetx = 0;
		targety = 0;
	}
	
	private void doWrapAround() {
		if(wrapAround){
			targetx %= maxWidth;
			targety %= maxHeight;
		}
	}

	public void moveTo(double xf, double yf) {
		double ang = Math.atan2(yf-y, xf-x);
		double distance = Math.sqrt(Math.pow(yf, 2) + Math.pow(y, 2));
		moveAng(ang, distance);
		
	}
	public void moveAng(double rad, double distance) {
		double finalDistance = Math.min(maxspeed, distance);
		targetx = x + Math.sin(rad) * finalDistance;
		targety = y + Math.cos(rad) * finalDistance;
	}
	private void repastSync(){
		cs.moveTo(this, x, y, 1);
		grid.moveTo(this, (int) x, (int) y, 1);
	}
}
