package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Labor;

@Repository
@Mapper
public interface LaborMapper {
	void upsert(Labor labor);
	Labor getLabor(Integer id);
}
