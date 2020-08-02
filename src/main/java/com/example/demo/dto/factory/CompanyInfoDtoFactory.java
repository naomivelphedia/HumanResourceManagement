package com.example.demo.dto.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CompanyInfoDto;
import com.example.demo.form.CompanyInfoForm;

@Component
public class CompanyInfoDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public CompanyInfoDto upsert(CompanyInfoForm form) {
		return new CompanyInfoDto(
				form.getCutoff_date()
		);
	}
}
