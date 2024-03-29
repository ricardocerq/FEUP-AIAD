package mars;

public class EntityGlobals {
	
	private static double commRange;
	
	private static double detectionRange;
	
	private static double entityMaxSpeed;
	
	private static int extractionSpeed;
	
	private static int gatherSpeed;
	
	private static double interactionRange;
	
	private static int maxCapacity;
	
	private static double maxEnergy;
	
	private static int maxHeight;
	
	private static int maxMineralValue;
	
	private static int maxWidth;
	
	private static int minMineralValue;
	
	private static double passiveDischarge;
	
	private static double rechargeRate;
	
	private static int scanSpeed;
	
	private static int unloadSpeed;
	
	private static int mineralTimerValue;
	
	private static int explorationSubdivisions;
	
	private static int maxSpottersContracted;
	
	private static int maxProducersContracted;
	
	private static int maxTransportersContracted;
	
	private static double breakProbability;
	
	private static int maxBreakingTicks;
	
	private static double costTravelingTime;
	
	private static double costWorkingTime;
	
	private static double costTravelingEnergy;
	
	private static double costWorkingEnergy;
	
	private static double costCarrying;
	
	private static boolean decideTravelingEnergy;
	
	private static boolean decideWorkingEnergy;
	
	
	private static boolean decideCapacity;
	
	public static int getMineralTimerValue() {
		return mineralTimerValue;
	}

	public static void setMineralTimerValue(int mineralTimerValue) {
		EntityGlobals.mineralTimerValue = mineralTimerValue;
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
	
	public static int getMaxMineralValue() {
		return maxMineralValue;
	}

	public static int getMaxWidth() {
		return maxWidth;
	}

	public static int getMinMineralValue() {
		return minMineralValue;
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

	public static void setMaxMineralValue(int maxMineralValue) {
		EntityGlobals.maxMineralValue = maxMineralValue;
	}

	public static void setMaxWidth(int maxWidth) {
		EntityGlobals.maxWidth = maxWidth;
	}

	public static void setMinMineralValue(int minMineralValue) {
		EntityGlobals.minMineralValue = minMineralValue;
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

	public static int getExplorationSubdivisions() {
		return explorationSubdivisions;
	}

	public static void setExplorationSubdivisions(int explorationSubdivisions) {
		EntityGlobals.explorationSubdivisions = explorationSubdivisions;
	}

	public static int getMaxSpottersContracted() {
		return maxSpottersContracted;
	}

	public static void setMaxSpottersContracted(int maxSpottersContracted) {
		EntityGlobals.maxSpottersContracted = maxSpottersContracted;
	}

	public static int getMaxProducersContracted() {
		return maxProducersContracted;
	}

	public static void setMaxProducersContracted(int maxProducersContracted) {
		EntityGlobals.maxProducersContracted = maxProducersContracted;
	}

	public static int getMaxTransportersContracted() {
		return maxTransportersContracted;
	}

	public static void setMaxTransportersContracted(int maxTransportersContracted) {
		EntityGlobals.maxTransportersContracted = maxTransportersContracted;
	}

	public static double getBreakProbability() {
		return breakProbability;
	}

	public static void setBreakProbability(double breakProbability) {
		EntityGlobals.breakProbability = breakProbability;
	}

	public static int getMaxBreakingTicks() {
		return maxBreakingTicks;
	}

	public static void setMaxBreakingTicks(int maxBreakingTicks) {
		EntityGlobals.maxBreakingTicks = maxBreakingTicks;
	}

	public static double getCostTravelingTime() {
		return costTravelingTime;
	}

	public static void setCostTravelingTime(double costTravelingTime) {
		EntityGlobals.costTravelingTime = costTravelingTime;
	}

	public static double getCostWorkingTime() {
		return costWorkingTime;
	}

	public static void setCostWorkingTime(double costWorkingTime) {
		EntityGlobals.costWorkingTime = costWorkingTime;
	}

	public static double getCostTravelingEnergy() {
		return costTravelingEnergy;
	}

	public static void setCostTravelingEnergy(double costTravelingEnergy) {
		EntityGlobals.costTravelingEnergy = costTravelingEnergy;
	}

	public static double getCostWorkingEnergy() {
		return costWorkingEnergy;
	}

	public static void setCostWorkingEnergy(double costWorkingEnergy) {
		EntityGlobals.costWorkingEnergy = costWorkingEnergy;
	}

	public static double getCostCarrying() {
		return costCarrying;
	}

	public static void setCostCarrying(double costCarrying) {
		EntityGlobals.costCarrying = costCarrying;
	}


	public static boolean getDecideCapacity() {
		return decideCapacity;
	}

	public static void setDecideCapacity(boolean decideCapacity) {
		EntityGlobals.decideCapacity = decideCapacity;
	}

	public static boolean getDecideTravelingEnergy() {
		return decideTravelingEnergy;
	}

	public static void setDecideTravelingEnergy(boolean decideTravelingEnergy) {
		EntityGlobals.decideTravelingEnergy = decideTravelingEnergy;
	}

	public static boolean getDecideWorkingEnergy() {
		return decideWorkingEnergy;
	}

	public static void setDecideWorkingEnergy(boolean decideWorkingEnergy) {
		EntityGlobals.decideWorkingEnergy = decideWorkingEnergy;
	}


	
}
