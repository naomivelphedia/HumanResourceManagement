package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Attendance {
	private Integer user_id;
	private Integer date_id;
	private Integer work_division_id;
	private String work_start;
	private String work_end;
	private Integer break_time;
	private String remarks;
	private String approval;
}
