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

	public void setTime(int time) {
		fTime = time;
	}

	public void setPreviousNode(Clsc previousNode) {
		fPreviousNode = previousNode;
	}

	public Clsc getPreviousNode() {
		return fPreviousNode;
	}

	public static Integer getDistanceBetweenTwoNodes(Clsc startNode, Clsc endNode) {
		return startNode.getNeigbourghs().get(endNode);
	}


}
