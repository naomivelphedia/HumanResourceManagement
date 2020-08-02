package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CompanyInfoDto;
import com.example.demo.entity.CompanyInfo;
import com.example.demo.entity.factory.CompanyInfoEntityFactory;
import com.example.demo.mapper.CompanyMapper;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyInfoEntityFactory companyInfoEntityFactory;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private CompanyInfo companyInfo;

	/**
	 * @param companyInfoDto
	 */
	public void upsertCompanyInfo(CompanyInfoDto companyInfoDto) {
		companyInfo = companyMapper.getCompanyInfo();
		if (companyInfo == null) {
			companyMapper.insert(companyInfoEntityFactory.upsert(companyInfoDto));
		} else {
			companyMapper.update(companyInfoEntityFactory.upsert(companyInfoDto));
		}
	}
}
