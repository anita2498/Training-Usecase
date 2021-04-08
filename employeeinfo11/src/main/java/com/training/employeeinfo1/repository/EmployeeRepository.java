package com.training.employeeinfo1.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.training.employeeinfo1.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	List<Employee> findByLastModifiedAfter(Timestamp t);

}
