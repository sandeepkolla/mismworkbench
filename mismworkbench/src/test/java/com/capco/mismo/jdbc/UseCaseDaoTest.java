package com.capco.mismo.jdbc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capco.mismo.jdbc.model.Element;
import com.capco.mismo.model.GraphLink;
import com.capco.mismo.model.GraphNode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UseCaseDaoTest {
	
	@Autowired
	UseCaseDao useCaseDao;
	
	@Test
	public void testGetElementsByUseCaseId() {
		List<Element> uceList = useCaseDao.getElementsByUseCaseId(Long.parseLong("2"));
		Assert.assertNotNull("should not be null.", uceList);
		Assert.assertTrue(uceList.size() > 0);
	}
	
	@Test
	public void testFetchContainersByUseCaseId() {
		List<GraphNode> graphNodes = useCaseDao.fetchContainers(Long.parseLong("3"));
		Assert.assertNotNull("should not be null.", graphNodes);
		Assert.assertTrue(graphNodes.size() > 0);System.out.println(graphNodes);
	}
	
	@Test
	public void testFetchContainers() {
		List<GraphNode> graphNodes = useCaseDao.fetchContainers();
		Assert.assertNotNull("should not be null.", graphNodes);
		Assert.assertTrue(graphNodes.size() > 0);System.out.println(graphNodes);
	}
	
	
	@Test
	public void testFetchElementsByUseCaseId() {
		List<GraphNode> graphNodes = useCaseDao.fetchElements(Long.parseLong("3"));
		Assert.assertNotNull("should not be null.", graphNodes);
		Assert.assertTrue(graphNodes.size() > 0);System.out.println(graphNodes);
	}
	
	@Test
	public void testFetchElements() {
		List<GraphNode> graphNodes = useCaseDao.fetchElements();
		Assert.assertNotNull("should not be null.", graphNodes);
		Assert.assertTrue(graphNodes.size() > 0);System.out.println(graphNodes);
	}
	
	@Test
	public void testFetchLinksByUseCaseId() {
		List<GraphLink> links = useCaseDao.fetchLinks(Long.parseLong("3"));
		Assert.assertNotNull("should not be null.", links);
		Assert.assertTrue(links.size() > 0);System.out.println(links);
	}
	
	@Test
	public void testFetchLinks() {
		List<GraphLink> links = useCaseDao.fetchLinks();
		Assert.assertNotNull("should not be null.", links);
		Assert.assertTrue(links.size() > 0);System.out.println(links);
	}

}
