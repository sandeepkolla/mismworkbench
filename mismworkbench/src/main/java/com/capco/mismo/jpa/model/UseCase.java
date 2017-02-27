package com.capco.mismo.jpa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="usecase")
public class UseCase implements Serializable {


	private static final long serialVersionUID = 7515413614991591870L;
	
	
	private Long id;

	
	private String name;

	
	private String descr;
	

	private Set<Container> containers;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "`usecase-container`", joinColumns = @JoinColumn(name = "usecase_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "container_id", referencedColumnName = "id"))
	public Set<Container> getContainers() {
		return containers;
	}

	public void setContainers(Set<Container> containers) {
		this.containers = containers;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
		return "UseCase [id=" + id + ", name=" + name + ", descr=" + descr + "]";
	}

	
	
	

}
