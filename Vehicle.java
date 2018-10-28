/**
 * Vehicle : Stores battery value and checks if a move is possible
 * 	> The multipliers have been converted from %/h in %/min (rounded)
 * 		> ex: 6%/h = 6%/(min*60) = 0.1%/min
 */
public class Vehicle { 

	
	private final int RISK_LEVEL;	// 1 = Low risk, 2 = Medium risk, 3 = High risk
	private double battery_;
	private double multiplier_;
	
	/**
	 * @param transportType (int) : 1 = Low risk, 2 = Medium risk, 3 = High risk (default case)
	 * Battery set to 100%
	 */
	Vehicle(int transportType) {
		RISK_LEVEL = transportType;
		switch(RISK_LEVEL) {
		case 1:
			multiplier_ = 0.1;
			break;
		case 2:
			multiplier_ = 0.2;
			break;
		default:	// case 3
			multiplier_ = 0.8;
		}
		battery_ = 100;
	}
	
	// Copy constructor, we only want to copy the risk level and the multiplier. The battery is set to full.
	public Vehicle(Vehicle copy) {
		RISK_LEVEL = copy.RISK_LEVEL;
		multiplier_ = copy.multiplier_;
		battery_ = 100;
	}
	
	public double chargeLeft() {
		return battery_;
	}
	
	public void charge() {
		battery_ = 100;
	}
	
	// Updates the battery
	public void travelFor(int timeTaken) {
		battery_ = battery_ - timeTaken * multiplier_;
	}
	
	/** Calculates the remaining charge of the battery after timeTaken of travel.
	 * @return true if > 20%; else returns false
	 * Note: timeTaken is in MINUTES
	 */
	public boolean moveIsPossible(int timeTaken) {
		double remainingBattery = battery_ - timeTaken * multiplier_;
		if (remainingBattery < 20)
			return false;
		return true;
	}
	
	/*
	 * Switches from NI-NH to LI-ion. The battery is reset to full.
	 */
	public void switchType() {
		battery_ = 100;
		switch(RISK_LEVEL) {
		case 1 :
			multiplier_ = 0.083;
			break;
		case 2 :
			multiplier_ = 0.167;
			break;
		default :
			multiplier_ = 0.5;
		}
	}
};
