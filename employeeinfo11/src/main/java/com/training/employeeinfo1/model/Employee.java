package com.training.employeeinfo1.model;



import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonPropertyOrder({"Id", "Firstname", "Lastname", "Department", "Manager", "DOJ", "dateCreated", "lastModified"})
public class Employee {
	
	public Employee(int id, String firstname, String lastname, String department, String manager, Date dOJ) {
		super();
		Id = id;
		Firstname = firstname;
		Lastname = lastname;
		Department = department;
		Manager = manager;
		DOJ = dOJ;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("Id")
	private int Id;
	@JsonProperty("Firstname")
	private String Firstname;
	@JsonProperty("Lastname")
	private String Lastname;
	@JsonProperty("Department")
	private String Department;
	@JsonProperty("Manager")
	private String Manager;
	@JsonProperty("DOJ")
	private Date DOJ;
	
	//@Column(updatable = false)
	@CreationTimestamp
	@JsonProperty("dateCreated")
    private Timestamp dateCreated;
    @UpdateTimestamp
    @JsonProperty("lastModified")
    private Timestamp lastModified;

	
}
