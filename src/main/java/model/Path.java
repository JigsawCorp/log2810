package main.java.model;

import jdk.jshell.execution.Util;

public class Path {
	
	private Clsc fPreviousNode;
	private int fTime;
	
	public Path(Clsc previousNode, int time) {
		fPreviousNode = previousNode;
		fTime = time;
	}
	
	public int getTime() {
		return fTime;
	}

	

}
