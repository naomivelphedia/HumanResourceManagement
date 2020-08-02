package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.CompanyInfoDto;
import com.example.demo.entity.CompanyInfo;

@Component
public class CompanyInfoEntityFactory {

	/**
	 * @param dto
	 * @return
	 */
	public CompanyInfo upsert(CompanyInfoDto dto) {
		return new CompanyInfo(
				dto.getCutoff_date()
		);
	}
}
