package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CompanyInfo;

@Repository
@Mapper
public interface CompanyMapper {
	CompanyInfo getCompanyInfo();
	void insert(CompanyInfo companyInfo);
	void update(CompanyInfo companyInfo);
}
