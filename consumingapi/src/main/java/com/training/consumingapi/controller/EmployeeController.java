package com.training.consumingapi.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.training.consumingapi.model.Employee;
import com.training.consumingapi.service.EmployeeService;

@RestController

@RequestMapping("/myapp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	RestTemplate restTemplate;

	public static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@GetMapping("/allemployees")
	public List<Employee> getEmployees() {
		logger.info("<ENTER> get all employees, Scheduler is invoked");
		return employeeService.getEmployees();

	}

	@GetMapping("/emp/{id}")
	//@Scheduled(cron = "${cron.expression}")
	public Employee getById(@PathVariable Integer id) {
		logger.info("<ENTER> access employee details by ID");
		return employeeService.getById(id);
	}

	/*
	 * @GetMapping("/updated")
	 * 
	 * @Scheduled(cron = "${cron.expression}") public List<Employee> getJobDetails()
	 * { return employeeService.getJobDetails(); }
	 */
	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.BAD_REQUEST.value());

	}

}
