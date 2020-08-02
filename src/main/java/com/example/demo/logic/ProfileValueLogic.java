package com.example.demo.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProfileValueLogic {

	/**
	 * @param birth_day
	 * @return
	 */
	public int getAge(Date birth_day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String nowStr = sdf.format(nowDate);
		String birthDayStr = sdf.format(birth_day);
		return (Integer.parseInt(nowStr) - Integer.parseInt(birthDayStr)) / 10000;
	}

	/**
	 * @param zipcode
	 * @return
	 */
	public String getPrevZip(String zipcode) {
		String prev_zip = zipcode.substring(0, 3);
		return prev_zip;
	}

	/**
	 * @param zipcode
	 * @return
	 */
	public String getNextZip(String zipcode) {
		String next_zip = zipcode.substring(3);
		return next_zip;
	}
}
