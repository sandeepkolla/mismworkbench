package com.capco.mismo.model;

import java.io.Serializable;

public class Container implements Serializable {
	
	private static final long serialVersionUID = 4114803008546268794L;

	private Long id;
	
    private String name;

	
	public Container(Long id, String name, String descr) {
		super();
		this.id = id;
		this.name = name;
		this.descr = descr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	private String descr;
	
	
	private Container parentContainer;
	private Container childContainer;
	public Container getParentContainer() {
		return parentContainer;
	}
	public void setParentContainer(Container parentContainer) {
		this.parentContainer = parentContainer;
	}
	public Container getChildContainer() {
		return childContainer;
	}
	public void setChildContainer(Container childContainer) {
		this.childContainer = childContainer;
	}
	
	

}
