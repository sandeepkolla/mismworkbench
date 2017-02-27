package com.capco.mismo.model;

import java.io.Serializable;
import java.util.Set;

public class UseCase implements Serializable{




	private static final long serialVersionUID = 1L;

	private Long id;


	private String name;


	private String descr;

	private Set<Container> containers;

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

	public Set<Container> getContainers() {
		return containers;
	}

	public void setContainers(Set<Container> containers) {
		this.containers = containers;
	}



}
