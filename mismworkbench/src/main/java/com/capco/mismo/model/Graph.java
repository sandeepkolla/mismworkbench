package com.capco.mismo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable {
	private static final long serialVersionUID = 8527838068796862917L;
	
	private List<GraphNode> nodes = new ArrayList<GraphNode>();
	private List<GraphLink> links = new ArrayList<GraphLink>();
	
	public List<GraphNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<GraphNode> nodes) {
		this.nodes = nodes;
	}
	public List<GraphLink> getLinks() {
		return links;
	}
	public void setLinks(List<GraphLink> links) {
		this.links = links;
	}
	
}
