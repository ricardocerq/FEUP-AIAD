package mars;

import java.util.Random;

public class Utils {
	public static Random r = new Random();
	
	public static final double delta = .1;
	
	public static boolean aproxSame(double v1, double v2){
		return Math.abs(v1-v2) < delta;
	}
	
	public static boolean  aproxZero(double v){
		return Math.abs(v) < delta;
	}
}
