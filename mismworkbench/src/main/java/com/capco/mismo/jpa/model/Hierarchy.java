package com.capco.mismo.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Hierarchy implements Serializable {

	private static final long serialVersionUID = -6170961322655993462L;

	private Long id;
	
	private Long parentId;
	
	private Long childId;
	
	private Container parentContainer;
	
	private Container childContainer;

	
	@ManyToOne
    @JoinColumn(name = "parent_id")
	public Container getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(Container parentContainer) {
		this.parentContainer = parentContainer;
	}

	@ManyToOne
    @JoinColumn(name = "child_id")
	public Container getChildContainer() {
		return childContainer;
	}

	@Override
	public String toString() {
		return "Hierarchy [id=" + id + ", parentId=" + parentId + ", childId=" + childId + "]";
	}

	public void setChildContainer(Container childContainer) {
		this.childContainer = childContainer;
	}

	@Id
	@Column(updatable = false, insertable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="parent_id", nullable = false, updatable = false, insertable = false)
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name="child_id", nullable = false, updatable = false, insertable = false)
	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}
	
	

}
