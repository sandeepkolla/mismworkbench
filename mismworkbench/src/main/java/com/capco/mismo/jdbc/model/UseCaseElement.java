package com.capco.mismo.jdbc.model;

import java.io.Serializable;

public class UseCaseElement implements Serializable {
	
	@Override
	public String toString() {
		return "UseCaseElement [id=" + id + ", useCaseId=" + useCaseId + ", containerElementId=" + containerElementId
				+ "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8007938240755969164L;

	private Long id;
	
	private Long useCaseId;
	
	private Long containerElementId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUseCaseId() {
		return useCaseId;
	}

	public void setUseCaseId(Long useCaseId) {
		this.useCaseId = useCaseId;
	}

	public Long getContainerElementId() {
		return containerElementId;
	}

	public void setContainerElementId(Long containerElementId) {
		this.containerElementId = containerElementId;
	}
	
	

}
