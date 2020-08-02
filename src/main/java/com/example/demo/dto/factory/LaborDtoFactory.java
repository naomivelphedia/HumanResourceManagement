package com.example.demo.dto.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LaborDto;
import com.example.demo.form.EmployeeLaborForm;

@Component
public class LaborDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public LaborDto upsert(EmployeeLaborForm form) {
		return new LaborDto(
				form.getUser_id(),
				form.getEmployment_status(),
				form.getBasic_salary(),
				form.getHourly_wage(),
				form.getWork_start(),
				form.getWork_end(),
				form.getScheduled_holiday(),
				form.getFixed_overtime(),
				form.getPass_price()
		);
	}
}
