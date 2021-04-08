package com.training.consumingapi.controller;

import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.consumingapi.model.Employee;
import com.training.consumingapi.service.EmployeeService;

@RestController
@EnableScheduling
@RequestMapping("/myapp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	RestTemplate restTemplate;

	/*
	 * @Value("${apiUrl.employee}") private String url;
	 * 
	 * static Timestamp t;
	 */
	@GetMapping("/allemployees")
	@Scheduled(fixedRate = 5000)
	public List<Employee> getEmployees() {
		return employeeService.getEmployees();
		

	}

	@GetMapping("/emp/{id}")
	public Employee getById(@PathVariable Integer id) {
		return employeeService.getById(id);
	}
	
	@GetMapping("/emp/latest/{t}")
	@Scheduled(cron= "${cronexpression}")
	public List<Employee> getLastUpdatedRecords(@PathVariable Timestamp t) {
		return employeeService.getLastUpdatedRecords(t);
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.BAD_REQUEST.value());

	}

}
