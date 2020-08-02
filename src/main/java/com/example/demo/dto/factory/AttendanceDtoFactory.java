package com.example.demo.dto.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.form.AttendanceForm;

@Component
public class AttendanceDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public AttendanceDto upsert(AttendanceForm form) {
		return new AttendanceDto(
				form.getUser_id(),
				form.getDate_id(),
				form.getWork_division_id(),
				form.getWork_start(),
				form.getWork_end(),
				form.getBreak_time(),
				form.getRemarks(),
				form.getApproval()
		);
	}
}
