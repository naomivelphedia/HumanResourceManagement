package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;

@Component
public class EmployeeEntityFactory {

	/**
	 * @param dto
	 * @return
	 */
	public Employee edit(EmployeeDto dto) {
		return new Employee(
				dto.getId(),
				dto.getName(),
				dto.getGender(),
				dto.getZipcode(),
				dto.getAddress(),
				dto.getTelnumber(),
				dto.getEmployment_status(),
				dto.getRole(),
				dto.getEmg_name(),
				dto.getRelation(),
				dto.getEmg_zipcode(),
				dto.getEmg_address(),
				dto.getEmg_telnumber()
		);
	}
}
