package com.training.consumingapi.service;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.consumingapi.controller.EmployeeController;
import com.training.consumingapi.model.Employee;
import com.training.consumingapi.model.Records;
import com.training.consumingapi.repository.RecordsRepository;

@Service
public class EmployeeService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private RecordsRepository recordsRepository;

	private Records record;

	@Value("${apiUrl.employee}")
	private String url;
	public static Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	static Timestamp t, tn;

	HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@Scheduled(cron = "${cron.expression}")
	public <T> List<Employee> getEmployees() {
		String getAllUrl = url;

		ParameterizedTypeReference<List<Employee>> typeRef = new ParameterizedTypeReference<List<Employee>>() {
		};
		ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<T>(createHeaders("afoo", "asecret")), typeRef);
		List<Employee> employeeList = responseEntity.getBody();

		Date date = new Date();
		tn = new Timestamp((long) date.getTime());
		t = new Timestamp((long) date.getTime() - (2 * 60000));
		int r = getLastUpdatedRecords(t).size();
		record = new Records();
		record.setStartTime(t);
		record.setStopTime(tn);
		record.setNoOfRecords(r);
		logger.info("Job Triggered : " + tn + "  No of records processed since " + t + " : " + r);
		// System.out.println("Job Triggered : " + tn + " No of records processed since
		// " + t + " : "+ r);

		recordsRepository.save(record);

		return employeeList;
	}

	public Employee getById(Integer id) {
		String getUrl = url + "/" + id;
		return restTemplate.getForObject(getUrl, Employee.class);
	}

	public <T> List<Employee> getLastUpdatedRecords(Timestamp t) {
		String getLastUpdatedUrl = url + "/latest/" + t;

		ParameterizedTypeReference<List<Employee>> typeRef = new ParameterizedTypeReference<List<Employee>>() {
		};
		ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(getLastUpdatedUrl, HttpMethod.GET,
				new HttpEntity<T>(createHeaders("afoo", "asecret")), typeRef);
		List<Employee> updatedRecords = responseEntity.getBody();

		return updatedRecords;
	}

}
