package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Labor {
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
