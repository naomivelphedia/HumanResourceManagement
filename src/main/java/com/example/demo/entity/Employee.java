package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Employee {
	private Integer id;
	private String name;
	private String gender;
	private String zipcode;
	private String address;
	private String telnumber;
	private int employment_status;
	private int role;
	private String emg_name;
	private String relation;
	private String emg_zipcode;
	private String emg_address;
	private String emg_telnumber;
}
