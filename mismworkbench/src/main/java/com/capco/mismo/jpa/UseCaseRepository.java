package com.capco.mismo.jpa;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.capco.mismo.jpa.model.UseCase;


public interface UseCaseRepository extends CrudRepository<UseCase, Long> {
	
	
	@Cacheable("usecase")
	UseCase findById(Long useCaseId);
	

}
