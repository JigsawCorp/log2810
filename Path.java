
public class Path {
	
	Clsc first_;
	Clsc second_;
	int time_;
	
	public Path(Clsc first, Clsc second, int time) {
		first_ = first;
		second_ = second;
		time_ = time;
	}
	
	public Clsc getDestination_() {
		return destination_;
	}
	public int getTime_() {
		return time_;
	}
	
}