package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LaborDto;
import com.example.demo.entity.Labor;

@Component
public class LaborEntityFactory {

	/**
	 * @param dto
	 * @return
	 */
	public Labor upsert(LaborDto dto) {
		return new Labor(
				dto.getUser_id(),
				dto.getEmployment_status(),
				dto.getBasic_salary(),
				dto.getHourly_wage(),
				dto.getWork_start(),
				dto.getWork_end(),
				dto.getScheduled_holiday(),
				dto.getFixed_overtime(),
				dto.getPass_price()
		);
	}
}
