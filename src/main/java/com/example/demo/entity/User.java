package com.example.demo.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User{
	private Integer id;
	private String name;
	private Date birth_day;
	private String gender;
	private String zipcode;
	private String address;
	private String telnumber;
	private String email;
	private String password;
	private int employment_status;
	private Date hire_day;
	private int role;
	private String emg_name;
	private String relation;
	private String emg_zipcode;
	private String emg_address;
	private String emg_telnumber;
}
