package com.capco.mismo.model;

import java.io.Serializable;

public class GraphNode implements Serializable {
	private static final long serialVersionUID = 8527838068796862917L;
	
	private String id;
	private String name;
	private String descr;
	private String type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public GraphNode(String id, String name, String descr, String type) {
		super();
		this.id = id;
		this.name = name;
		this.descr = descr;
		this.type = type;
	}
	@Override
	public String toString() {
		return "GraphNode [id=" + id + ", name=" + name + ", descr=" + descr + ", type=" + type + "]";
	}
}
