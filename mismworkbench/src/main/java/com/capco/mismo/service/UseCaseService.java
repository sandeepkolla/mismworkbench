package com.capco.mismo.service;

import java.io.File;
import java.io.IOException;

import org.dom4j.DocumentException;

import com.capco.mismo.jpa.model.UseCase;
import com.capco.mismo.model.Graph;
import com.capco.mismo.model.GraphUI;

public interface UseCaseService {
	
	UseCase findUseCase(Long useCaseId);
	
	public void createUseCase(String name, String desc);
	
	public void deleteUseCase(Long useCaseId);
	
	File useCaseSchema(Long useCaseId) throws DocumentException, IOException ;
	
	public Graph getGraphFromUsecase(Long useCaseId);
	
	public Graph getGraph();
	
	public void addToUseCase(GraphUI graphUi, Long useCaseId);

}
