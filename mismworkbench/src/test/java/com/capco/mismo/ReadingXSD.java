package com.capco.mismo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ReadingXSD {

	private static final String COMPLEX_TYPE = "complexType";
	private static final String ELEMENT_TYPE = "element";
	private static final String SEQUENCE = "sequence";
	
	public static void main(String[] args) throws DocumentException, IOException {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("src/test/java/com/capco/mismo/mismo.xsd"));
		Element rootElement = doc.getRootElement();
		Iterator<Element> elementIterator = rootElement.elementIterator();
		while(elementIterator.hasNext()){
			Element element = elementIterator.next();
			handleElements(element);
			
		}
	}
	
	public static void handleElements(Element element) throws IOException {

		String name = element.attributeValue("name");
		if(isComplexType(element) || isSequenceType(element)) {
			Iterator<Element> elementIterator = element.elementIterator();
			while (elementIterator.hasNext()) {
				handleElements(elementIterator.next());
			}
		} else {
			if(isElement(element)) {
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter writer = new XMLWriter( System.out, format );
				writer.write( element );
			}
		}

	}
	
	private static boolean isSequenceType(Element element){
		return element.getName().equals(SEQUENCE);
	}
	
	private static boolean isComplexType(Element element){
		return element.getName().equals(COMPLEX_TYPE);
	}
	
	private static boolean isElement(Element element){
		return element.getName().equals(ELEMENT_TYPE);
	}
}
