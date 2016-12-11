package onto;

import jade.content.Concept;
import mars.Mineral;

public class DepositFact implements Concept {
	private static final long serialVersionUID = 1L;
	
	public double locationx;

	public double getLocationx() {
		return locationx;
	}

	public void setLocationx(double locationx) {
		this.locationx = locationx;
	}

	public double getLocationy() {
		return locationy;
	}

	public void setLocationy(double locationy) {
		this.locationy = locationy;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean getScanCompleted() {
		return scanCompleted;
	}

	public void setScanCompleted(boolean scanCompleted) {
		this.scanCompleted = scanCompleted;
	}

	public int getAmountScanned() {
		return amountScanned;
	}

	public void setAmountScanned(int amountScanned) {
		this.amountScanned = amountScanned;
	}

	public int getAmountExtracted() {
		return amountExtracted;
	}

	public void setAmountExtracted(int amountExtracted) {
		this.amountExtracted = amountExtracted;
	}

	public double locationy;
	public int time;
	public boolean scanCompleted;
	public int amountScanned;
	public int amountExtracted;

	public DepositFact(Mineral m, int t) {
		this.locationx = m.getX();
		this.locationy = m.getY();
		this.scanCompleted = m.getScannable() == 0;
		this.amountExtracted = m.getExtracted();
		this.amountScanned = m.getScanned();
		this.time = t;
	}

	public DepositFact() {

	}

}
