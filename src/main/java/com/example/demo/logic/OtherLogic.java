package com.example.demo.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.service.OtherService;

@Component
public class OtherLogic {

	@Autowired
	private OtherService otherService;

	/**
	 * @param num
	 * @return
	 * 数値を0埋めする(1→01,12→12)
	 */
	public String getZeroPadding(int num) {
		String num_zero = "0" + String.valueOf(num);
		return num_zero.substring(num_zero.length() - 2);
	}

	/**
	 * @param time
	 * @return
	 * 時間を"00:00"形式に変換
	 */
	public String timeFormatChange(int time) {
		String hour = String.valueOf(time / 60);
		String minute_zero = "0" + String.valueOf(time % 60);
		String minute = minute_zero.substring(minute_zero.length() - 2);
		return hour + ":" + minute;
	}

	/**
	 * @param currentYearMonth
	 * @param dateValue
	 * @return
	 */
	public String getCurrentYearMonth(String currentYearMonth, String dateValue) {
		if (currentYearMonth.contains(",")) {
			currentYearMonth = currentYearMonth.replace(",", "0");
			String currentYear = currentYearMonth.substring(0, 4);
			String currentMonth = currentYearMonth.substring(currentYearMonth.length() - 2);
			currentYearMonth = currentYear + currentMonth;
		}
		if (!dateValue.equals("")) {
			int currentMonthInt = Integer.parseInt(currentYearMonth.substring(4)) + Integer.parseInt(dateValue);
			int currentYear = Integer.parseInt(currentYearMonth.substring(0, 4));
			if (currentMonthInt == 0) {
				currentYear--;
				currentMonthInt = 12;
			} else if (currentMonthInt == 13) {
				currentYear++;
				currentMonthInt = 1;
			}
			String currentMonthStr = getZeroPadding(currentMonthInt);
			currentYearMonth = String.valueOf(currentYear) + currentMonthStr;
		}
		if (dateValue.equals("") && currentYearMonth.equals("")) {
			Date nowDate = otherService.getNowDate();
			currentYearMonth = new SimpleDateFormat("yyyyMM").format(nowDate);
		}
		return currentYearMonth;
	}

	/**
	 * @return
	 */
	public String getNowDate() {
		Date nowDate = otherService.getNowDate();
		return new SimpleDateFormat("yyyyMM").format(nowDate);
	}

	/**
	 * @param yearMonth
	 * @return
	 */
	public int getYear(String yearMonth) {
		return Integer.parseInt(yearMonth.substring(0, 4));
	}

	/**
	 * @param yearMonth
	 * @return
	 */
	public int getMonthInt(String yearMonth) {
		return Integer.parseInt(yearMonth.substring(4));
	}

	/**
	 * @param yearMonth
	 * @return
	 */
	public String getMonthStr(String yearMonth) {
		return yearMonth.substring(4);
	}
}
