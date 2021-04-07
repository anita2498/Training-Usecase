package com.training.employeeinfo1.repository;

import org.springframework.data.repository.CrudRepository;

import com.training.employeeinfo1.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	//public Employee findById(int id);

}
