import java.util.ArrayList;

public class ClscNode {
	private Clsc destination_;		// corresponds to the key
	private ArrayList<Clsc> pathToPoint_;
	private int timeBeforeCharge_;		// in minutes
	private int timeAfterCharge_;		// Only is set if vehicle has stopped to charge
	
	public ClscNode(Clsc dest) {
		destination_ = dest;
		pathToPoint_ = new ArrayList<Clsc>();
		timeBeforeCharge_ = Integer.MAX_VALUE;	// Set to infinity by default
		timeAfterCharge_ = Integer.MAX_VALUE;
		pathToPoint_.add(dest);
	}

	
	public Clsc getDestination_() {
		return destination_;
	}

	public void setDestination_(Clsc destination_) {
		this.destination_ = destination_;
	}
	
	public ArrayList<Clsc> getPathToPoint_() {
		return pathToPoint_;
	}

	public void setPathToPoint_(ArrayList<Clsc> pathToPoint_) {
		this.pathToPoint_ = pathToPoint_;
	}

	public int getTotalTime() {
		return timeBeforeCharge_ + timeAfterCharge_;
	}
	
	public int getTime_() {
		return timeBeforeCharge_;
	}
	
	public int getTimeAfterCharge() {
		return timeAfterCharge_;
	}
	
	public void setTime_(int time_) {
		this.timeBeforeCharge_ = time_;
	}
	
	public void addToPath(Clsc clsc) {
		pathToPoint_.add(clsc);
	}
	
	public void addNextNode(ClscNode clsc) {
		
		pathToPoint_ = new ArrayList<Clsc>(clsc.getPathToPoint_());
		pathToPoint_.add(destination_);
		timeBeforeCharge_ = clsc.getTime_();
		/*
		if (timeBeforeCharge_ == Integer.MAX_VALUE)
			timeBeforeCharge_ = 0;
		timeBeforeCharge_ += clsc.getTime_();
		*/
	}
	
	
	public void update(ClscNode clsc, int time) {
		int updatedTime = clsc.getTime_() + time;
		// Only update if the CLSC is not in the path and the time is lower
		if (!pathToPoint_.contains(clsc.getDestination_()) && updatedTime < timeBeforeCharge_) {
			timeBeforeCharge_ = updatedTime;
			pathToPoint_= new ArrayList<Clsc>(clsc.getPathToPoint_());	// Path from the CLSC it came from
			pathToPoint_.add(destination_);
		}
	}		
	
	
	public ClscNode combine(ClscNode nodeToAdd) {
		timeBeforeCharge_ += 120;	// The time of both paths are added and 120 minutes are added for the charge time
		timeAfterCharge_ = nodeToAdd.getTime_();
		destination_ = nodeToAdd.getDestination_();			// destination updated to the end CLSC
		//nodeToAdd.getPathToPoint_().remove(0);	// Removed the first element of the second array so there are no duplicates
		ArrayList<Clsc> combinedPaths = new ArrayList<Clsc>(pathToPoint_);
		combinedPaths.addAll(nodeToAdd.getPathToPoint_());	// Both arrays are combined
		pathToPoint_ = combinedPaths;
		return this;
	}	
}
