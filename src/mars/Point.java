package mars;

public class Point {
	private double x;
	private double y;

	public Point(double x2, double y2) {
		this.x = x2;
		this.y = y2;
	}
	
	public Point(){
		this(0,0);
	}
	
	public double getX() {
		return x;
	}
	
	public Point setX(double x) {
		this.x = x;
		return this;
	}
	
	public double getY() {
		return y;
	}
	
	public Point setY(double y) {
		this.y = y;
		return this;
	}
	
	public Point addSet(Point p2){
		this.x += p2.x;
		this.y += p2.y;
		return this;
	}
	
	public Point add(Point p2){
		return this.copy().addSet(p2);
	}
	
	public Point subSet(Point p2){
		this.x -= p2.x;
		this.y -= p2.y;
		return this;
	}
	
	public Point sub(Point p2){
		return this.copy().subSet(p2);
	}
	
	public Point scalarMulSet(double mul){
		this.x *= mul;
		this.y *= mul;
		return this;
	}
	
	public Point scalarMul(double mul){
		return this.copy().scalarMulSet(mul);
	}
	
	public Point linCombSet(double mul, Point p2){
		return this.addSet(p2.scalarMul(mul));
	}
	
	public Point linComb(double mul, Point p2){
		return this.add(p2.scalarMul(mul));
	}
	
	public Point copy(){
		return new Point(this.x, this.y);
	}
}
