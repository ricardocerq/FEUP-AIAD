package mars;

public class EntityGlobals {
	
	private static int maxWidth;
	private static int maxHeight;
	private static double entityMaxSpeed;
	
	public static int getMaxWidth() {
		return maxWidth;
	}
	
	public static void setMaxWidth(int maxWidth) {
		EntityGlobals.maxWidth = maxWidth;
	}
	
	public static int getMaxHeight() {
		return maxHeight;
	}
	
	public static void setMaxHeight(int maxHeight) {
		EntityGlobals.maxHeight = maxHeight;
	}
	
	private static double maxEnergy;
	private static double rechargeRate;
	private static double commRange;
	private static double detectionRange;
	private static double interactionRange;
	private static double passiveDischarge;
	
	public static double getMaxEnergy() {
		return maxEnergy;
	}

	public static void setMaxEnergy(double maxEnergy) {
		EntityGlobals.maxEnergy = maxEnergy;
	}
	
	public static double getRechargeRate() {
		return rechargeRate;
	}

	public static void setRechargeRate(double rechargeRate) {
		EntityGlobals.rechargeRate = rechargeRate;
	}
	
	public static double getCommRange() {
		return commRange;
	}

	public static void setCommRange(double commRange) {
		EntityGlobals.commRange = commRange;
	}
	
	public static double getDetectionRange() {
		return detectionRange;
	}

	public static void setDetectionRange(double detectionRange) {
		EntityGlobals.detectionRange = detectionRange;
	}

	public static double getInteractionRange() {
		return interactionRange;
	}

	public static void setInteractionRange(double interactionRange) {
		EntityGlobals.interactionRange = interactionRange;
	}
	
	public static double getPassiveDischarge() {
		return passiveDischarge;
	}

	public static void setPassiveDischarge(double passiveDischarge) {
		EntityGlobals.passiveDischarge = passiveDischarge;
	}
	
	private static int maxCapacity;
	public static double getEntityMaxSpeed() {
		return entityMaxSpeed;
	}

	public static void setEntityMaxSpeed(double entityMaxSpeed) {
		EntityGlobals.entityMaxSpeed = entityMaxSpeed;
	}

	private static int scanSpeed;
	private static int extractionSpeed;
	private static int gatherSpeed;
	
	public static int getMaxCapacity() {
		return maxCapacity;
	}

	public static void setMaxCapacity(int maxCapacity) {
		EntityGlobals.maxCapacity = maxCapacity;
	}

	public static int getScanSpeed() {
		return scanSpeed;
	}

	public static void setScanSpeed(int scanSpeed) {
		EntityGlobals.scanSpeed = scanSpeed;
	}

	public static int getExtractionSpeed() {
		return extractionSpeed;
	}

	public static void setExtractionSpeed(int extractionSpeed) {
		EntityGlobals.extractionSpeed = extractionSpeed;
	}

	public static int getGatherSpeed() {
		return gatherSpeed;
	}

	public static void setGatherSpeed(int gatherSpeed) {
		EntityGlobals.gatherSpeed = gatherSpeed;
	}
	
}
