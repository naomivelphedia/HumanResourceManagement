package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoForm {
	private Integer id;
	private String name;
	private String gender;
	private String zip3;
	private String zip4;
	private String address;
	private String telnumber;
	private int employment_status;
	private int role;
	private String emg_name;
	private String relation;
	private String emg_zip3;
	private String emg_zip4;
	private String emg_address;
	private String emg_telnumber;
}
