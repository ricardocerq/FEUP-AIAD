package mars;


import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import sajas.core.Agent;

public abstract class Entity extends Agent {
	private static List<Entity> entities = new ArrayList<Entity>();
	private static int maxWidth;
	private static int maxHeight;
	private static double entityMaxSpeed;
	
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
		entities.add(this);
	}
	public Entity(Context<Object> context,ContinuousSpace<Object> cs, Grid<Object> grid, double maxspeed){
		this(context, cs, grid, maxspeed, Math.random()*maxWidth, Math.random()*maxHeight);
	}
	
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
			targetx %= maxWidth;
			targety %= maxHeight;
		} else {
			targetx = targetx < 0 ? 0 : targetx;
			targety = targety < 0 ? 0 : targety;
			targetx = targetx >= maxWidth ? maxWidth : targetx;
			targety = targety >= maxHeight ? maxHeight : targety;
		}
	}
	
	public void moveRandomTo(double dx, double dy, double maxRange){
		double ang;
		double sgn = Math.random() - 0.5;
		if (sgn > 0)
			ang = heading + Math.random()*50;
		else
			ang = heading - Math.random()*50;
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
		double ang;
		double sgn = Math.random() - 0.5;
		if (sgn > 0)
			ang = heading + Math.random()*50;
		else
			ang = heading - Math.random()*50;
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
	
	public List<Entity> getCloseBy(double radius, Class c){
		List<Entity> ret = new ArrayList<>();
		for(Entity e: entities){
			if(e.getClass().equals(c) && e != this){
				double distance = dist(this.x, this.y, e.x, e.y);
				if(distance < radius){
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
	
	public double dist(Entity e){
		return dist(this.x, this.y, e.x, e.y);
	}
}
