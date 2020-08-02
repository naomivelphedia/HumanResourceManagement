package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceForm {
	private Integer user_id;
	private String date_id;
	private String work_division_id;
	private String work_start;
	private String work_end;
	private String break_time;
	private String remarks;
	private String approval;
}
