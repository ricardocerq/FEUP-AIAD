package mars;

public class EntityGlobals {
	
	private static int maxWidth;
	
	private static int maxHeight;
	
	private static double entityMaxSpeed;
	
	private static double maxEnergy;
	
	private static double rechargeRate;
	
	private static double commRange;
	
	private static double detectionRange;
	
	private static double interactionRange;
	
	private static double passiveDischarge;
	
	private static int maxCapacity;
	
	private static int scanSpeed;
	
	private static int extractionSpeed;
	
	private static int gatherSpeed;
	
	private static int unloadSpeed;
	
	private static int maxMineralValue;
	private static int minMineralValue;
	
	public static void setMaxMineralValue(int maxMineralValue) {
		EntityGlobals.maxMineralValue = maxMineralValue;
	}
	public static void setMinMineralValue(int minMineralValue) {
		EntityGlobals.minMineralValue = minMineralValue;
	}
	public static int getMaxMineralValue() {
		return maxMineralValue;
	}
	public static int getMinMineralValue() {
		return minMineralValue;
	}
	
	public static double getCommRange() {
		return commRange;
	}
	
	public static double getDetectionRange() {
		return detectionRange;
	}

	public static double getEntityMaxSpeed() {
		return entityMaxSpeed;
	}
	
	public static int getExtractionSpeed() {
		return extractionSpeed;
	}

	public static int getGatherSpeed() {
		return gatherSpeed;
	}
	
	public static double getInteractionRange() {
		return interactionRange;
	}

	public static int getMaxCapacity() {
		return maxCapacity;
	}

	public static double getMaxEnergy() {
		return maxEnergy;
	}

	public static int getMaxHeight() {
		return maxHeight;
	}
	
	public static int getMaxWidth() {
		return maxWidth;
	}

	public static double getPassiveDischarge() {
		return passiveDischarge;
	}
	
	public static double getRechargeRate() {
		return rechargeRate;
	}
	public static int getScanSpeed() {
		return scanSpeed;
	}

	public static int getUnloadSpeed() {
		return unloadSpeed;
	}

	public static void setCommRange(double commRange) {
		EntityGlobals.commRange = commRange;
	}
	
	public static void setDetectionRange(double detectionRange) {
		EntityGlobals.detectionRange = detectionRange;
	}
	
	public static void setEntityMaxSpeed(double entityMaxSpeed) {
		EntityGlobals.entityMaxSpeed = entityMaxSpeed;
	}
	
	public static void setExtractionSpeed(int extractionSpeed) {
		EntityGlobals.extractionSpeed = extractionSpeed;
	}
	
	public static void setGatherSpeed(int gatherSpeed) {
		EntityGlobals.gatherSpeed = gatherSpeed;
	}

	public static void setInteractionRange(double interactionRange) {
		EntityGlobals.interactionRange = interactionRange;
	}

	public static void setMaxCapacity(int maxCapacity) {
		EntityGlobals.maxCapacity = maxCapacity;
	}

	public static void setMaxEnergy(double maxEnergy) {
		EntityGlobals.maxEnergy = maxEnergy;
	}

	public static void setMaxHeight(int maxHeight) {
		EntityGlobals.maxHeight = maxHeight;
	}

	public static void setMaxWidth(int maxWidth) {
		EntityGlobals.maxWidth = maxWidth;
	}

	public static void setPassiveDischarge(double passiveDischarge) {
		EntityGlobals.passiveDischarge = passiveDischarge;
	}

	public static void setRechargeRate(double rechargeRate) {
		EntityGlobals.rechargeRate = rechargeRate;
	}

	public static void setScanSpeed(int scanSpeed) {
		EntityGlobals.scanSpeed = scanSpeed;
	}

	public static void setUnloadSpeed(int unloadSpeed) {
		EntityGlobals.unloadSpeed = unloadSpeed;
	}
	
}
