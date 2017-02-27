package com.capco.mismo.jpa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;



@Entity
public class Container implements Serializable{

	private static final long serialVersionUID = 7515413614991591870L;

	
	private Long id;

	private Hierarchy hierarchy;

	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
	public Hierarchy getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Hierarchy hierarchy) {
		this.hierarchy = hierarchy;
	}

	private String name;

	
	private String descr;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = true)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}


	@Override
	public String toString() {
		return "Container [id=" + id + ", name=" + name + ", descr=" + descr + "]";
	}

}
