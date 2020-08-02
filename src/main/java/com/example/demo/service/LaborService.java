package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LaborDto;
import com.example.demo.entity.Labor;
import com.example.demo.entity.factory.LaborEntityFactory;
import com.example.demo.mapper.LaborMapper;

@Service
public class LaborService {

	@Autowired
	private LaborMapper laborMapper;
	@Autowired
	private LaborEntityFactory laborEntityFactory;
	@Autowired
	private Labor labor;

	/**
	 * @param labor
	 */
	public void upsertLabor(LaborDto labor) {
		laborMapper.upsert(laborEntityFactory.upsert(labor));
	}

	/**
	 * @param id
	 * @return
	 */
	public Labor getLabor(Integer id) {
		return laborMapper.getLabor(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public int getBasic_salary(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return 0;
		} else {
			return labor.getBasic_salary();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public int getHourly_wage(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return 0;
		} else {
			return labor.getHourly_wage();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public String getWorkStart(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return "00:00";
		} else {
			return labor.getWork_start();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public String getWorkEnd(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return "00:00";
		} else {
			return labor.getWork_end();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public String getFixed_overtime(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return "00:00";
		} else {
			return labor.getFixed_overtime();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public int getPass_price(Integer id) {
		labor = laborMapper.getLabor(id);
		if (labor == null) {
			return 0;
		} else {
			return labor.getPass_price();
		}
	}
}
