package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaborDto {
	private Integer user_id;
	private int employment_status;
	private int basic_salary;
	private int hourly_wage;
	private String work_start;
	private String work_end;
	private String scheduled_holiday;
	private String fixed_overtime;
	private int pass_price;
}
