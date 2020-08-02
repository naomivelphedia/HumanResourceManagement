package com.example.demo.dto.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.form.EmployeeInfoForm;

@Component
public class EmployeeDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public EmployeeDto employeeEdit(EmployeeInfoForm form) {
		return new EmployeeDto(
				form.getId(),
				form.getName(),
				form.getGender(),
				form.getZip3() + form.getZip4(),
				form.getAddress(),
				form.getTelnumber(),
				form.getEmployment_status(),
				form.getRole(),
				form.getEmg_name(),
				form.getRelation(),
				form.getEmg_zip3() + form.getEmg_zip4(),
				form.getEmg_address(),
				form.getEmg_telnumber()
		);
	}
}
