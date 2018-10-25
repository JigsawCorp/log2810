package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Clsc {
	
	int id_;
	boolean hasTerminal_;
	//ArrayList<Path> fPaths;
	//ArrayList<Clsc> fNeigbourghs;

	HashMap<Clsc, Integer> fNeigbourghs;
	
	public Clsc(int id, boolean hasTerminal) {
		id_ = id;
		hasTerminal_ = hasTerminal;
		fNeigbourghs = new HashMap<>();

	}
	
	public int getId() {
		return id_;
	}
	
	public boolean hasCharge() {
		return hasTerminal_;
	}
	
	//void addPath(Path path) {
	//	fPaths.add(path);
	//}

	//public ArrayList<Clsc> getNeigbourghs() {
	//	return fNeigbourghs;
	//}

	//public ArrayList<Path> getPaths() {
	//	return fPaths;
	//}

	public HashMap<Clsc, Integer> getNeigbourghs() {
		return fNeigbourghs;
	}
	
	public String toString() {
		return "CLSC" + id_ + ", " + hasTerminal_ + "\n";
	}
}