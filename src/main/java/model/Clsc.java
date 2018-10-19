package main.java.model;

import java.util.ArrayList;

public class Clsc {
	
	int id_;
	boolean hasTerminal_;
	ArrayList<Path> paths_;
	
	public Clsc(int id, boolean hasTerminal) {
		id_ = id;
		hasTerminal_ = hasTerminal;
		paths_ = new ArrayList<Path>();
	}
	
	public int getId() {
		return id_;
	}
	
	public boolean hasCharge() {
		return hasTerminal_;
	}
	
	void addPath(Path path) {
		paths_.add(path);
	}
	
	public String toString() {
		return "CLSC" + id_ + ", " + hasTerminal_ + "\n";
	}
}