package com.training.employeeinfo1.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.training.employeeinfo1.model.Employee;
import com.training.employeeinfo1.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	public EmployeeService employeeService;
	
	public static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		logger.info("<ENTER> get all employees");
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("/employees/latest/{t}")
	public List<Employee> getLastModifiedRecords(@PathVariable String t) {
		logger.info("<ENTER> get records of last processed");
		return employeeService.getLastModifiedRecords(Timestamp.valueOf(t));
	}

	@GetMapping("/employees/{id}")
	public Employee getEmployee(@PathVariable Integer id) {
		logger.info("<ENTER> get employee by Id");
		return employeeService.getEmployee(id);
	}

	@PostMapping("/employees")
	public void addEmployee(@RequestBody Employee employee) {
		logger.info("<ENTER> add an employee");
		employeeService.addEmployee(employee);
	}
	 
	@PutMapping("/employees/{id}")
	public void updateEmployee(@RequestBody Employee employee, @PathVariable Integer id) {
		logger.info("<ENTER> update an employee");
		employeeService.updateEmployee(employee, id);
	}

	@DeleteMapping("/employees/{id}")
	public void deleteEmployee(@PathVariable Integer id)  {
		logger.info("<ENTER> delete an employee");
		employeeService.deleteEmployee(id);
		
	}
	
	@ExceptionHandler
    void handleIllegalArgumentException(
                      IllegalArgumentException e,
                      HttpServletResponse response) throws IOException {
 
        response.sendError(HttpStatus.BAD_REQUEST.value());
 
    }
}



