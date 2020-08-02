package com.example.demo.form;

import lombok.Data;

@Data
public class SignupForm {
	private String name;
	private Integer birth_year;
	private Integer birth_month;
	private Integer birth_date;
	private String gender;
	private String zip3;
	private String zip4;
	private String address;
	private String telnumber;
	private String email;
	private String password;
	private int employment_status;
	private Integer hire_year;
	private Integer hire_month;
	private Integer hire_date;
	private int role;
	private String emg_name;
	private String relation;
	private String emg_zip3;
	private String emg_zip4;
	private String emg_address;
	private String emg_telnumber;
}
