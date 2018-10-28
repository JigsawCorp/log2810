import java.util.ArrayList;

public class ClscNode {
	private Clsc destination_;		// corresponds to the key
	private ArrayList<Clsc> pathToPoint_;
	private int time_;		// in minutes
	
	public ClscNode(Clsc dest) {
		destination_ = dest;
		pathToPoint_ = new ArrayList<Clsc>();
		time_ = Integer.MAX_VALUE;	// Set to infinity by default
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

	public int getTime_() {
		return time_;
	}

	public void setTime_(int time_) {
		this.time_ = time_;
	}
	
	public void addToPath(Clsc clsc) {
		pathToPoint_.add(clsc);
	}
	
	public void update(ClscNode clsc, int time) {
		int updatedTime = clsc.getTime_() + time;
		// Only update if the CLSC is not in the path and the time is lower
		if (!pathToPoint_.contains(clsc.getDestination_()) && updatedTime < time_) {
			time_ = updatedTime;
			pathToPoint_= new ArrayList<Clsc>(clsc.getPathToPoint_());	// Path from the CLSC it came from
			pathToPoint_.add(destination_);
		}
	}
	
	public ClscNode combine(ClscNode nodeToAdd) {
		time_ += nodeToAdd.getTime_() + 120;	// The time of both paths are added and 120 minutes are added for the charge time
		destination_ = nodeToAdd.getDestination_();			// destination updated to the end CLSC
		nodeToAdd.getPathToPoint_().remove(0);	// Removed the first element of the second array so there are no duplicates
		pathToPoint_.addAll(nodeToAdd.getPathToPoint_());	// Both arrays are combined
		
		return this;
	}
	
}
