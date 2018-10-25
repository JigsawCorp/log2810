/**
 * Vehicle : Stores battery value and checks if a move is possible
 */
public enum Vehicle { 
	// The multipliers have been converted from %/h in %/min (rounded)
	// ex: 6%/h = 6%/(min*60) = 0.1%/min
	LOW_RISK(0.1, 0.083), 
	MEDIUM_RISK(0.2, 0.167), 
	HIGH_RISK(0.8, 0.5);
	private final double NimhMultiplier_;
	private final double LiionMultiplier_;
	
	private double battery_;
	private double multiplier_;
	
	Vehicle(double mult1, double mult2) {
		NimhMultiplier_ = mult1;
		LiionMultiplier_ = mult2;
		battery_ = 100;
		multiplier_ = NimhMultiplier_;
	}
	
	public double chargeLeft() {
		return battery_;
	}
	
	public void charge() {
		battery_ = 100;
	}
	
	/* Calculates the remaining charge of the battery after timeTaken of travel.
	 * Returns the remaining charge and updates it if > 20%; if not, returns Integer.MAX_VALUE
	 * Note: timeTaken is in MINUTES
	 */
	public double travelFor(int timeTaken) {
		double remainingBattery = battery_ - timeTaken * multiplier_;
		if (remainingBattery < 20)
			return Integer.MAX_VALUE;
		battery_ = remainingBattery;
		return battery_;
	}
	
	/*
	 * Switches from NI-NH to LI-ion. The battery is reset to full.
	 */
	public void switchType() {
		multiplier_ = LiionMultiplier_;
		battery_ = 100;
	}
};
