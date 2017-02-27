package com.capco.mismo;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dom4j.DocumentException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capco.mismo.service.UseCaseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UseCaseServiceTest {
	
	@Autowired
	UseCaseService useCaseService;
	
	@Test
	public void testUseCaseSchema() throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {
		
		try {
			File useCaseSchema = useCaseService.useCaseSchema(Long.parseLong("2"));
			Assert.assertNotNull("cannot be null", useCaseSchema);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@Rollback
	public void testCreateUseCase() {
		useCaseService.createUseCase("test2", "test desc");
	}
}
