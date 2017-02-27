package com.capco.mismo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capco.mismo.jdbc.model.Element;
import com.capco.mismo.jdbc.model.UseCaseElement;
import com.capco.mismo.model.GraphLink;
import com.capco.mismo.model.GraphNode;

@Repository
public class UseCaseDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	
	@Transactional(readOnly = true)
	public List<Element> getElementsByUseCaseId(Long useCaseId) {
		return jdbcTemplate.query("select e.id, e.name, e.descr from `usecase-element` ue join `container-element` ce on ue.container_element_id = ce.id join element e ON e.id = ce.element_id where ue.usecase_id = ?", new Object[] { useCaseId },
				new ElementRowMapper());
	}
	
	@Transactional(readOnly = true)
	public List<GraphNode> fetchContainers(Long useCaseId) {
		return jdbcTemplate.query("select concat('c',`container`.`id`) as id, `container`.`name`, `container`.`descr`, 'container' as type " +
				  "from `container` " + 
				  "inner join `usecase-container` on `usecase-container`.`container_id` = `container`.`id` " + 
				  "where `usecase-container`.`usecase_id` in (?)", new Object[] { useCaseId }, new GraphNodeRowMapper());
		
	}
	
	
	@Transactional(readOnly = true)
	public List<GraphNode> fetchContainers() {
		return jdbcTemplate.query("select concat('c',`container`.`id`) as id, `container`.`name`, `container`.`descr`, 'container' as type " +
				  "from `container` ", 
				   new GraphNodeRowMapper());
		
	}
	
	
	@Transactional(readOnly = true)
	public List<GraphNode> fetchElements(Long useCaseId) {
		return jdbcTemplate.query("select  `element`.`name`,concat('e',`element`.`id`) as id, `element`.`descr`, 'element' as type " +
								  "from `container-element` " + 
								  "inner join `usecase-element` on `usecase-element`.`container_element_id` = `container-element`.`id` " + 
								  "inner join `element` on `element`.`id` = `container-element`.`element_id` " +
								  "where `usecase-element`.`usecase_id` in (?)", new Object[] { useCaseId }, new GraphNodeRowMapper());
		
	}
	
	@Transactional(readOnly = true)
	public List<GraphNode> fetchElements() {
		return jdbcTemplate.query("select  `element`.`name`,concat('e',`element`.`id`) as id, `element`.`descr`, 'element' as type " +
				  "from `element` " , new GraphNodeRowMapper());
		
	}
	
	public List<GraphLink> fetchLinks(Long useCaseId){
		return jdbcTemplate.query("select distinct concat('c',h.parent_id) as source, concat('c', h.child_id) as target from hierarchy h " +
								  "inner join `usecase-container` ucc on ucc.container_id = h.child_id " +
								  "inner join `usecase-container` ucp on ucp.container_id = h.parent_id " +
								  "where ucp.usecase_id in (?)", new Object[] {useCaseId}, new GraphLinkRowMapper());
	}
	
	
	public List<GraphLink> fetchLinks(){
		return jdbcTemplate.query("select concat('c',h.parent_id) as source, concat('c', h.child_id) as target from hierarchy h " ,  new GraphLinkRowMapper());
	}
	
	public List<GraphLink> fetchContainerElementLinks(){
		return jdbcTemplate.query("select concat('c',h.container_id) as source, concat('c', h.element_id) as target from `container-element` h " ,  new GraphLinkRowMapper());
	}
	
	
	class UseCaseElementRowMapper implements RowMapper<UseCaseElement> {
		@Override
		public UseCaseElement mapRow(ResultSet rs, int rowNum) throws SQLException {
			UseCaseElement user = new UseCaseElement();
			user.setId(rs.getLong("id"));
			user.setContainerElementId(rs.getLong("container_element_id"));
			user.setUseCaseId(rs.getLong("usecase_id"));
			return user;
		}
	}
	
	class ElementRowMapper implements RowMapper<Element> {
		@Override
		public Element mapRow(ResultSet rs, int rowNum) throws SQLException {
			Element element = new Element();
			element.setId(rs.getLong("id"));
			element.setName(rs.getString("name"));
			element.setDesc(rs.getString("descr"));
			return element;
		}
	}
	
	class GraphNodeRowMapper implements RowMapper<GraphNode> {
		@Override
		public GraphNode mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GraphNode(rs.getString("id"), rs.getString("name"), rs.getString("descr"), rs.getString("type"));
		}
	}
	
	class GraphLinkRowMapper implements RowMapper<GraphLink> {
		@Override
		public GraphLink mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GraphLink(rs.getString("source"), rs.getString("target"));
		}
	}
}
