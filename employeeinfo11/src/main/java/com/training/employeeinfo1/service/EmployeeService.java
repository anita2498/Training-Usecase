package com.training.employeeinfo1.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.employeeinfo1.model.Employee;
import com.training.employeeinfo1.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAll().forEach(employees::add);
		return employees;
	}
	
	public List<Employee> getLastModifiedRecords(Timestamp t) {
		List<Employee> records = new ArrayList<>();
		employeeRepository.findByLastModifiedAfter(t).forEach(records::add);
		System.out.println("Number of records processed  :  " + records.size());
		return records;
	}

	public Optional<Employee> getEmployee(Integer id) {
		return employeeRepository.findById(id);
	}

	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);	
	}

	public void updateEmployee(Employee employee, Integer id) {
		employeeRepository.save(employee);	
	}

	public void deleteEmployee(Integer id) {
		employeeRepository.deleteById(id);
		
	}
}
