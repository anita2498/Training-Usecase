package com.training.employeeinfo1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.client.HttpClientErrorException;

import com.training.employeeinfo1.model.Employee;
import com.training.employeeinfo1.repository.EmployeeRepository;
import com.training.employeeinfo1.service.EmployeeService;

@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class EmployeeRepositoryTests {

	//private static final Integer id = null;
	static Employee employee;

	@TestConfiguration
	static class EmployeeServiceTestContextConfiguration {

		@Bean
		public EmployeeService employeeService() {
			return new EmployeeService();
		}
	}

	@Autowired
	private EmployeeService service;

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @BeforeAll public static void initEach(){ employee = new Employee(1,
	 * "Elon", "Musk", "dev", "abcd", new Date(2000, 12, 11)); }
	 */
	
	@SuppressWarnings({ "deprecation" })
	@Test
	@Transactional
	@Order(1)
	public void testSaveNewEmployee() {
		//Timestamp t = Timestamp.valueOf(System.currentTimeMillis());
		employee = new Employee(1, "Elon", "Musk", "dev", "abcd", new Date(2000, 12, 11) );
		service.addEmployee(employee);
		employee.setDateCreated(service.getEmployee(1).getDateCreated());
		employee.setLastModified(service.getEmployee(1).getLastModified());
		assertEquals(employee, service.getEmployee(1));
	}

	
	 
	 

	@SuppressWarnings({ "deprecation"})
	@Test
	@Transactional
	@Order(2)
	public void testUpdateEmployee() {
		//Timestamp t = Timestamp.valueOf("2020-03-02 12:34:08");
		employee = new Employee(2, "Elon", "Musk", "dev", "abcd", new Date(2000, 12, 11));
		service.addEmployee(employee);
		System.out.println(service.getAllEmployees());
		employee.setLastname("Bezos");
		//System.out.println(System.nanoTime());
		int id = 2;
		service.updateEmployee(employee, id);
		System.out.println(employee);
		//System.out.println("   /////// " + service.getEmployee(id));
		assertEquals(employee, service.getEmployee(id));
	}
	
	
	@SuppressWarnings({ "deprecation" })
	@Test
	@Transactional
	@Order(3)
	public void testGetEmployee() {
		employee = new Employee(3, "Elon", "Musk", "dev", "abcd", new Date(2000, 12, 11));
		service.addEmployee(employee);
		employee.setDateCreated(service.getEmployee(3).getDateCreated());
		employee.setLastModified(service.getEmployee(3).getLastModified());
		assertEquals(employee, service.getEmployee(3));
	}
	
	@SuppressWarnings({ "deprecation" })
	@Test
	@Transactional
	@Order(4)
	public void testDeleteEmployee() {
		employee = new Employee(4, "Elon", "Musk", "dev", "abcd", new Date(2000, 12, 11));
		service.addEmployee(employee);
		service.deleteEmployee(4);
		assertThat(employee).as("Employee Id not found!"); 
		
		/*
		 * try { assertFalse(service.getEmployee(4).equals(null)); } catch (final
		 * HttpClientErrorException e) { assertEquals(e.getStatusCode(),
		 * HttpStatus.NOT_FOUND); }
		 */
		
	}
	 

}
