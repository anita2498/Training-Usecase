package com.training.consumingapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.consumingapi.model.Employee;

@RestController
@RequestMapping("/myapp")
public class EmployeeController {
	
	
	
	
	
	
	@Autowired
	   RestTemplate restTemplate;
	
		/*
		 * @GetMapping("/allemployees") public List<Object> getEmployees(){
		 * 
		 * String url = "http://localhost:8082/employees"; Object[] objects
		 * =restTemplate.getForObject(url, Object[].class);
		 * 
		 * return Arrays.asList(objects);
		 * 
		 * }
		 */
	@Value("${apiUrl.employee}")
	private String url;
	
	
	@GetMapping("/allemployees")
	public List<Employee> getEmployees(){
		
		String getAllUrl = url ;
		List<Employee> employeeList =new ObjectMapper().convertValue(
				restTemplate.getForObject(getAllUrl, List.class),
				new TypeReference<List<Employee>>() { });
			return employeeList;
		
		
	}
	
	@GetMapping("/emp/{id}")
	public Employee getById(@PathVariable Integer id) {
		String getUrl= url + "/" + id;
		return restTemplate.getForObject(getUrl, Employee.class);
	}
	
	
	
	
	
	
	
	
	

}
