package com.capco.mismo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capco.mismo.jpa.model.Container;
import com.capco.mismo.jpa.model.Hierarchy;
import com.capco.mismo.jpa.model.UseCase;
import com.capco.mismo.model.Graph;
import com.capco.mismo.model.GraphUI;
import com.capco.mismo.service.UseCaseService;

@RestController
public class MismoWorkbenchController {

	@Autowired
	UseCaseService useCaseService;


	@RequestMapping("/usecase")
	public @ResponseBody com.capco.mismo.model.UseCase subSchema(@RequestParam(value="id", defaultValue="1") String id) {
		UseCase useCase = useCaseService.findUseCase(Long.parseLong(id));
		com.capco.mismo.model.UseCase useCaseUi = new com.capco.mismo.model.UseCase();
		useCaseUi.setId(useCase.getId());
		useCaseUi.setName(useCase.getName());
		useCaseUi.setDescr(useCase.getDescr());
		Set<com.capco.mismo.model.Container> containersUi = new HashSet<com.capco.mismo.model.Container>();
		useCaseUi.setContainers(containersUi);
		Set<Container> containers = useCase.getContainers();
		for (Container container : containers) {
			com.capco.mismo.model.Container containerUi = createUiContainers(container);
			containersUi.add(containerUi);
		}
		return useCaseUi;
	}

	private com.capco.mismo.model.Container createUiContainers(Container container) {
		com.capco.mismo.model.Container containerUi = new com.capco.mismo.model.Container(container.getId(), container.getName(), container.getDescr());
		Hierarchy hierarchy = container.getHierarchy();
		Container parentContainer = hierarchy.getParentContainer();
		Container childContainer = hierarchy.getChildContainer();
		com.capco.mismo.model.Container containerParent = new com.capco.mismo.model.Container(parentContainer.getId(), parentContainer.getName(), parentContainer.getDescr());
		com.capco.mismo.model.Container containerChild = new com.capco.mismo.model.Container(childContainer.getId(), childContainer.getName(), childContainer.getDescr());
		containerUi.setParentContainer(containerParent);
		containerUi.setChildContainer(containerChild);
		return containerUi;
	}

	@RequestMapping(value = "/useCaseSchema", method = RequestMethod.GET)
	public void downloadUseCaseSchema(@RequestParam(value="id", defaultValue="1") String id, HttpServletResponse response) throws IOException, NumberFormatException, DocumentException {
		File schemaFile = useCaseService.useCaseSchema(Long.parseLong(id));
		String mimeType = URLConnection.guessContentTypeFromName(schemaFile.getName());
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + schemaFile.getName() +"\""));
		response.setContentLength((int)schemaFile.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(schemaFile));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		FileUtils.deleteQuietly(schemaFile);
	}


	@RequestMapping(value = "/api/createUsecase", method = RequestMethod.POST)
	public void createUseCase(String name, String desc)  {
		useCaseService.createUseCase(name, desc);
	}
	
	@RequestMapping(value = "/api/deleteUsecase/{useCaseId}", method = RequestMethod.POST)
	public void deleteUseCase(@PathVariable Long useCaseId)  {
		useCaseService.deleteUseCase(useCaseId);
	}

	@RequestMapping(value="/api/getGraphForUsecase/{useCaseId}", method = RequestMethod.GET)
	public Graph getGraphFromUsecase(@PathVariable Long useCaseId){
		return useCaseService.getGraphFromUsecase(useCaseId);
	}

	@RequestMapping(value="/api/graph", method = RequestMethod.GET)
	public Graph getGraph(){
		return useCaseService.getGraph();
	}
	
	@RequestMapping(value="/api/addToUsecase/{useCaseId}", method = RequestMethod.POST)
	public void addToUsecase(@RequestBody GraphUI graphUi, @PathVariable Long useCaseId){
		System.out.println(graphUi);
		System.out.println(useCaseId);
	}
}
