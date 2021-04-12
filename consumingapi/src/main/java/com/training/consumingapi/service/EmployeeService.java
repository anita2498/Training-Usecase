package com.training.consumingapi.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.consumingapi.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${apiUrl.employee}")
	private String url;

	static Timestamp t,tn;

	@Scheduled(cron = "${cronexpression}")
	public List<Employee> getEmployees() {
		String getAllUrl = url;
		List<Employee> employeeList = new ObjectMapper().convertValue(restTemplate.getForObject(getAllUrl, List.class),
				new TypeReference<List<Employee>>() {
				});
		Date date = new Date();
		tn = new Timestamp((long) date.getTime());
		t= new Timestamp((long) date.getTime() - (5*60000));
		System.out.println("Job Triggered : " + tn + "  No of records processed since " + t + " : " + getLastUpdatedRecords(t).size());
		return employeeList;
	}
	
	
	
	
	
	
	
	

	
	public Employee getById(Integer id) {
		String getUrl = url + "/" + id;
		return restTemplate.getForObject(getUrl, Employee.class);
	}

	public List<Employee> getLastUpdatedRecords(Timestamp t) {
		String getLastUpdatedUrl = url + "/latest/" + t;
		List<Employee> updatedRecords = new ObjectMapper().convertValue(
				restTemplate.getForObject(getLastUpdatedUrl, List.class), new TypeReference<List<Employee>>() {
				});
		return updatedRecords;
	}
	
	

}
