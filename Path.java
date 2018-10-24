
public class Path {
	
	Clsc destination_;
	int time_;
	
	public Path(Clsc destination, int time) {
		destination_ = destination;
		time_ = time;
	}
	
	public Clsc getDestination() {
		return destination_;
	}
	public int getTime() {
		return time_;
	}
	
}