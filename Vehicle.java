/**
 * Vehicle : Stores battery value and checks if a move is possible
 */
public enum Vehicle { 
	LOW_RISK(6, 5), 
	MEDIUM_RISK(12, 10), 
	HIGH_RISK(48, 30);
	private final int NimhMultiplier_;
	private final int LiionMultiplier_;
	
	private int battery_;
	private int multiplier_;
	
	Vehicle(int mult1, int mult2) {
		NimhMultiplier_ = mult1;
		LiionMultiplier_ = mult2;
		battery_ = 100;
		multiplier_ = NimhMultiplier_;
	}
	
	public int chargeLeft() {
		return battery_;
	}
	
	public void charge() {
		battery_ = 100;
	}
	
	/* Calculates the remaining charge of the battery after timeTaken of travel.
	 * Returns the remaining charge and updates it if > 20%; if not, returns Integer.MAX_VALUE
	 */
	public int travelFor(int timeTaken) {
		int remainingBattery = battery_ - timeTaken * multiplier_;
		if (remainingBattery < 20)
			return Integer.MAX_VALUE;
		battery_ = remainingBattery;
		return battery_;
	}
	
	public void switchType() {
		multiplier_ = LiionMultiplier_;
	}
};
