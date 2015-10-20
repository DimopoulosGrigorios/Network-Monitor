package android;

import java.util.ArrayList;



public class AvailableNodes  {

	private ArrayList<String> nodes;

	public AvailableNodes() {

		nodes = new ArrayList<String>();
	}

	public ArrayList<String> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}

	public void insert(String a) {
		nodes.add(a);
	}

	
}