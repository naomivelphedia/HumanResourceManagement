package com.example.demo.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Labor;
import com.example.demo.service.LaborService;

@Component
public class EmployeeLaborLogic {

	@Autowired
	private LaborService laborService;
	@Autowired
	private Labor labor;

	/**
	 * @param id
	 * @return
	 */
	public Labor getEmployeeLabor(Integer id) {
		labor = laborService.getLabor(id);
		if (labor == null) {
			Labor defaultLabor = new Labor();
			defaultLabor.setEmployment_status(0);
			defaultLabor.setBasic_salary(0);
			defaultLabor.setHourly_wage(0);
			defaultLabor.setWork_start("00:00");
			defaultLabor.setWork_end("00:00");
			defaultLabor.setScheduled_holiday("土,日,祝日");
			defaultLabor.setFixed_overtime("00:00");
			defaultLabor.setPass_price(0);
			labor = defaultLabor;
		}
		return labor;
	}
}
