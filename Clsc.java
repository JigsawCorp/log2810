import java.util.ArrayList;

public class Clsc implements Comparable<Clsc> {
	
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
	
	public ArrayList<Path> getPaths() {
		return paths_;
	}
	
	public void addPath(Path path) {
		paths_.add(path);
	}
	
	public int timeToGetTo(Clsc destination) {			
		for (Path path : paths_) {
			if (path.getDestination() == destination)
				return path.getTime();
		}
		// if (destination == this || !paths_.contains(destination))
		return 0;
	}
	
	public int compareTo(Clsc other) {
		return Integer.compare(id_, other.id_);
	}
}