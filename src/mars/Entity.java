package mars;


import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import sajas.core.Agent;

public abstract class Entity extends Agent {
	protected static List<Entity> entities = new ArrayList<Entity>();
	
	
	
	public double getMaxspeed() {
		return maxspeed;
	}
	
	public void setMaxspeed(double maxspeed) {
		this.maxspeed = maxspeed;
	}
	
	private static boolean wrapAround = false;
	private double x;
	private double y;
	private double maxspeed;
	private double targetx;
	private double targety;
	private double heading;
	private double nextheading;
	private Context<Object> context;
	private ContinuousSpace<Object> cs;
	private Grid<Object> grid;
	
	public static void clearEntities(){
		entities.clear();
	}
	
	public Entity(Context<Object> context, ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed, double x, double y){
		entities.add(this);
		context.add(this);
		this.context = context;
		this.cs = cs;
		this.grid = grid;
		this.maxspeed = maxspeed;
		this.x = x;
		this.y = y;
		this.targetx = x;
		this.targety = y;
		repastSync();
	}
	public Entity(Context<Object> context,ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed){
		this(context, cs, grid, maxspeed, Math.random()*EntityGlobals.getMaxWidth(), Math.random()*EntityGlobals.getMaxHeight());
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
	
	@ScheduledMethod(start = 1, interval = 1)
	public void stepRepast(){
		commitMove();
		commitActions();
		repastSync();
	}
	
	private void commitActions() {
		//for(EntityAction e : )
	}

	public void commitMove(){
		double dist = dist(x, y, targetx, targety);
		doWrapAround();
		x = targetx;
		y = targety;
		heading = nextheading;
		this.onMove(dist);
	}
	
	private void doWrapAround() {
		if(wrapAround){
			targetx %= EntityGlobals.getMaxWidth();
			targety %= EntityGlobals.getMaxHeight();
		} else {
			targetx = targetx < 0 ? 0.001 : targetx;
			targety = targety < 0 ? 0.001 : targety;
			targetx = targetx >= EntityGlobals.getMaxWidth() ? EntityGlobals.getMaxWidth()-.001 : targetx;
			targety = targety >= EntityGlobals.getMaxHeight() ? EntityGlobals.getMaxHeight()-.001 : targety;
		}
	}
	
	private double randomTurn(){
		double ang;
		double sgn = Math.random() - 0.5;
		if (sgn > 0)
			ang = heading + Math.random()*Math.PI/8;
		else
			ang = heading - Math.random()*Math.PI/8;
		return ang;
	}
	
	public void moveRandomTo(double dx, double dy, double maxRange){
		double ang = randomTurn();
		double rx = Math.cos(ang) * maxRange;
		double ry = Math.sin(ang) * maxRange;
		
		double fx = rx + dx;
		double fy = ry + dy;
		
		moveTo(x+fx,y+fy,maxRange);
	}
	
	public void moveTo(double xf, double yf, double maxRange) {
		
		double distance = dist(x, y, xf, yf);
		
		double toMove = Math.max(0, Math.min(maxRange, distance));
		
		moveAng(angleTo(xf, yf), toMove);
		
	}
	
	public void moveAway(double xf, double yf, double maxRange) {
		
		double distance = dist(x, y, xf, yf);
		
		double toMove = Math.max(0, Math.min(maxRange, distance));
		
		moveAng(angleTo(xf, yf)+Math.PI/2, toMove);
		
	}
	
	
	public double angleTo(double xf, double yf){
		return Math.atan2(yf-y, xf-x);
	}
	
	public void moveRandom(double maxRange){
		double ang = randomTurn();
		moveAng(ang, maxRange);
	}
	
	public void moveAng(double rad, double distance) {
		double finalDistance = distance;
		targetx = x + Math.cos(rad) * finalDistance;
		targety = y + Math.sin(rad) * finalDistance;
		nextheading = rad;
	}
	
	public void repastSync(){
		cs.moveTo(this, x, y, 1);
		grid.moveTo(this, (int) x, (int) y);
	}
	//int num;
	public List<Entity> getCloseBy(double radius, Class c, boolean includeSelf){
		//System.out.println(++num);
		double radiusSquare = radius*radius;
		List<Entity> ret = new ArrayList<>();
		for(Entity e: entities){
			if((e.getClass().equals(c) || e.getClass().getSuperclass().equals(c)) && (includeSelf || e != this)){
				double distSquare = distSquare(this.x, this.y, e.x, e.y);
				if(distSquare < radiusSquare){
					ret.add(e);
				}
			}
		}
		return ret;
	}
	
	protected abstract void onMove(double dist);
	
	public abstract double maxRange();
	
	public static double dist(double x1, double y1, double x2, double y2){
		return  Math.sqrt(Math.pow(y1-y2, 2) + Math.pow(x1-x2, 2));
	}
	
	public static double distSquare(double x1, double y1, double x2, double y2){
		return Math.pow(y1-y2, 2) + Math.pow(x1-x2, 2);
	}
	
	public double dist(Entity e){
		return dist(this.x, this.y, e.x, e.y);
	}
}
