package main.java.model;

public class Path {
	
	private Clsc first_;
	private Clsc second_;
	private int time_;
	
	public Path(Clsc first, Clsc second, int time) {
		first_ = first;
		second_ = second;
		time_ = time;
	}
	
	public int getTime() {
		return time_;
	}
	
	public String toString() {
		return " Path : " + first_ + ", " + second_ + ", temps : " + time_ + "\n";
	}
	
}