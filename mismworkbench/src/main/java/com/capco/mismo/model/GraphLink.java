package com.capco.mismo.model;

import java.io.Serializable;

public class GraphLink implements Serializable {
	@Override
	public String toString() {
		return "GraphLink [source=" + source + ", target=" + target + "]";
	}
	private static final long serialVersionUID = 8527838068796862917L;
	private String source;
	private String target;
	public GraphLink(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

}
