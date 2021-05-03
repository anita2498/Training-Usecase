package com.training.consumingapi.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.Data;

@Data
@Entity
public class Records {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private Timestamp startTime;
	private Timestamp stopTime;
	private int noOfRecords;


}
