package com.example.demo.logic;

import org.springframework.stereotype.Component;

@Component
public class PayslipTableLogic {

	/**
	 * @param totalOverTime
	 * @return
	 */
	public int getOvertimePay(String totalOverTime) {
		int basicHourlyWage = 1000;
		String[] totalOverTimeStr = totalOverTime.split(":");
		int totalOverTimeHour = Integer.parseInt(totalOverTimeStr[0]);
		int totalOverTimeMinute = Integer.parseInt(totalOverTimeStr[1]);
		if (totalOverTimeMinute < 30) {
			totalOverTimeMinute = 0;
		} else {
			totalOverTimeHour++;
		}
		int overtimePay = basicHourlyWage * totalOverTimeHour;
		return overtimePay;
	}
}
