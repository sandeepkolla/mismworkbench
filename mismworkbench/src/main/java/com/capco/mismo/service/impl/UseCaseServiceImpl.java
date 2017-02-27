package com.capco.mismo.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.capco.mismo.jdbc.UseCaseDao;
import com.capco.mismo.jpa.UseCaseRepository;
import com.capco.mismo.jpa.model.Container;
import com.capco.mismo.jpa.model.UseCase;
import com.capco.mismo.model.Graph;
import com.capco.mismo.model.GraphLink;
import com.capco.mismo.model.GraphNode;
import com.capco.mismo.model.GraphUI;
import com.capco.mismo.model.Node;
import com.capco.mismo.service.UseCaseService;


@Component("useCaseService")
@Transactional
public class UseCaseServiceImpl implements UseCaseService {

	@Autowired
	UseCaseDao useCaseDao;
	
	private static final String SIMPLE_TYPE = "simpleType";
	private static final String ELEMENT_TYPE = "element";
	private static final String DOCUMENTATION = "documentation";
	private static final String ANNOTATION = "annotation";
	private static final String IMPORT = "import";
	private static final String SEQUENCE = "sequence";
	private static final String COMPLEX_TYPE = "complexType";

	private final UseCaseRepository useCaseRepository;

	public UseCaseServiceImpl(UseCaseRepository useCaseRepository) {
		this.useCaseRepository = useCaseRepository;
	}

	@Override
	public UseCase findUseCase(Long useCaseId) {
		Assert.notNull(useCaseId, "need use case id");
		return this.useCaseRepository.findById(useCaseId);
	}

	@Override
	public File useCaseSchema(Long useCaseId) throws DocumentException, IOException  {
		
		Set<String> containerNames = new HashSet<String>();
		UseCase useCase = this.findUseCase(useCaseId);
		Set<Container> containers = useCase.getContainers();
		for (Container container : containers) {
			containerNames.add(container.getName());
			containerNames.add(container.getHierarchy().getChildContainer().getName());
		}

		Set<String> elementNames = new HashSet<String>();
		List<com.capco.mismo.jdbc.model.Element> elements = useCaseDao.getElementsByUseCaseId(useCaseId);
		for (com.capco.mismo.jdbc.model.Element element : elements) {
			elementNames.add(element.getName());
		}

		SAXReader reader = new SAXReader();
		Document doc = reader.read(this.getClass().getClassLoader().getResourceAsStream("mismo.xsd"));
		Element rootElement = doc.getRootElement();
		Iterator<Element> elementIterator = rootElement.elementIterator();
		while(elementIterator.hasNext()){
			Element element = elementIterator.next();
			String name = element.attributeValue("name");
			System.out.println("*** working on element ***" + name);
			if(containerNames.contains(name)) {
				handleElements(element, elementNames);
			} else {
				if((!isImport(element) && !isDocumentation(element) && !isAnnotation(element) && !isSimpleType(element)) && name.equals(name.toUpperCase())) {
					System.out.println("Removing element " + name + " from root element");
					rootElement.remove(element);
				}
			}
		}
		return write(doc);
	}


	public File write(Document document) throws IOException {
		String useCaseSchemaFileName = "/useCase-mismo.xsd";
		String tmpLocation = System.getProperty("java.io.tmpdir");
		XMLWriter writer = new XMLWriter(new FileWriter( tmpLocation +useCaseSchemaFileName ));
		writer.write( document);
		writer.close();
		return new File(tmpLocation +useCaseSchemaFileName );
		// Pretty print the document to System.out
		//		OutputFormat format = OutputFormat.createPrettyPrint();
		//		writer = new XMLWriter( System.out, format );
		//		writer.write( document );

		// Compact format to System.out
		//		format = OutputFormat.createCompactFormat();
		//		writer = new XMLWriter( System.out, format );
		//		writer.write( document );
	}

	public void handleElements(Element element, Set<String> elementNames) {
		String name = element.attributeValue("name");
		if(isComplexType(element) || isSequenceType(element)) {
			if(isComplexType(element) || isSequenceType(element)) {
				System.out.println("*** Complex Or Sequence Element ****" + name);
			}
			Iterator<Element> elementIterator = element.elementIterator();
			while (elementIterator.hasNext()) {
				handleElements(elementIterator.next(), elementNames);
			}
		} else {
			if(isElement(element) && !isAnnotation(element) && !isDocumentation(element)){
				String elementName = name;
				System.out.println("*** Simple Element ****" + elementName);
				if(!elementName.equals(elementName.toUpperCase())) {
					if(!elementNames.contains(elementName)) {
						Element parent = element.getParent();
						System.out.println("removing element >>> " + elementName + " from parent element >>>>" + parent.attributeValue("name"));
						parent.remove(element);
					}
				}
			}
		}
	}

	private boolean isComplexType(Element element){
		return element.getName().equals(COMPLEX_TYPE);
	}

	private boolean isElement(Element element){
		return element.getName().equals(ELEMENT_TYPE);
	}

	private boolean isSequenceType(Element element){
		return element.getName().equals(SEQUENCE);
	}

	private boolean isImport(Element element){
		return element.getName().equals(IMPORT);
	}

	private boolean isAnnotation(Element element){
		return element.getName().equals(ANNOTATION);
	}

	private boolean isDocumentation(Element element){
		return element.getName().equals(DOCUMENTATION);
	}

	private boolean isSimpleType(Element element){
		return element.getName().equals(SIMPLE_TYPE);
	}


	@Override
	public void createUseCase(String name, String descr) {
		
		UseCase useCase = new UseCase();
		useCase.setName(name);
		useCase.setDescr(descr);
		useCaseRepository.save(useCase);
		
	}

	@Override
	public Graph getGraphFromUsecase(Long useCaseId) {
		Graph graph = new Graph();
		List<GraphNode> nodes = useCaseDao.fetchContainers(useCaseId);
		nodes.addAll(useCaseDao.fetchElements(useCaseId));
		graph.setNodes(nodes);
		graph.setLinks(useCaseDao.fetchLinks(useCaseId));
		return graph;
	}

	@Override
	public Graph getGraph() {
		Graph graph = new Graph();
		List<GraphNode> nodes = useCaseDao.fetchContainers();
		nodes.addAll(useCaseDao.fetchElements());
		graph.setNodes(nodes);
		List<GraphLink> links = useCaseDao.fetchLinks();
		links.addAll(useCaseDao.fetchContainerElementLinks());
		graph.setLinks(links);
		return graph;
	}

	@Override
	public void deleteUseCase(Long useCaseId) {
		useCaseRepository.delete(useCaseId);
	}

	@Override
	public void addToUseCase(GraphUI graphUi, Long useCaseId) {
		List<Node> nodes = graphUi.getNodes();
		for (Node node : nodes) {
			
		}
	}
}
