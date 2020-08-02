package com.example.demo.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Labor;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.LaborService;

@Component
public class AttendanceTableLogic {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private LaborService laborService;
	@Autowired
	private OtherLogic otherLogic;

	/**
	 * @param tableName
	 * @return
	 */
	public boolean tableExists(String tableName) {
		String table = attendanceService.findTable(tableName);
		if (table == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param currentYearMonth
	 * @return
	 */
	public boolean isWithin3yearsAgo(String currentYearMonth) {
		String nowYearMonth = otherLogic.getNowDate();
		int nowYear = otherLogic.getYear(nowYearMonth);
		int nowMonth = otherLogic.getMonthInt(nowYearMonth);
		int currentYear = otherLogic.getYear(currentYearMonth);
		int currentMonth = otherLogic.getMonthInt(currentYearMonth);
		if (nowYear - 2 <= currentYear) {
			return true;
		} else if (nowYear - 3 <= currentYear && nowMonth <= currentMonth) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * @return
	 */
	public String getDeleteTableName() {
		String nowYearMonth = otherLogic.getNowDate();
		int year = otherLogic.getYear(nowYearMonth);
		int monthInt = otherLogic.getMonthInt(nowYearMonth) - 1;
		if (monthInt == 0) {
			monthInt = 12;
			year--;
		}
		String month = otherLogic.getZeroPadding(monthInt);
		return "attendance" + String.valueOf(year - 3) + month;
	}

	/**
	 * @param currentYearMonth
	 * @return
	 */
	public boolean isFuture(String currentYearMonth) {
		String nowYearMonth = otherLogic.getNowDate();
		if (nowYearMonth.equals(currentYearMonth)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param currentYearMonth
	 * @return
	 */
	public boolean isPrevBtn(String currentYearMonth) {
		String nowYearMonth = otherLogic.getNowDate();
		int nowYear = otherLogic.getYear(nowYearMonth);
		int nowMonth = otherLogic.getMonthInt(nowYearMonth);
		int currentYear = otherLogic.getYear(currentYearMonth);
		int currentMonth = otherLogic.getMonthInt(currentYearMonth);
		if (nowYear - 3 == currentYear && nowMonth == currentMonth) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param id
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @return
	 * 出勤区分のデフォルト値
	 */
	public List<Integer> getDefaultDivisionIdList(Integer id, List<String> oneDayList, int endOfTheMonth) {
		List<Integer> defaultDivisionIdList = new ArrayList<Integer>();
		List<String> dayOfWeekList = getDayOfWeekList(oneDayList, endOfTheMonth);
		Labor labor = laborService.getLabor(id);
		if (labor != null) {
			for (int i = 0; i < endOfTheMonth; i++) {
				if (labor.getScheduled_holiday().contains(dayOfWeekList.get(i))) {
					defaultDivisionIdList.add(1);
				} else {
					defaultDivisionIdList.add(2);
				}
			}
		} else {
			for (int j = 0; j < endOfTheMonth; j++) {
				defaultDivisionIdList.add(1);
			}
		}
		return defaultDivisionIdList;
	}

	/**
	 * @param id
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @return
	 * 社員労務情報で設定した勤務開始時間のデフォルト値
	 */
	public List<String> getDefaultWorkStartList(Integer id, List<Integer> workDivisionIdList, int endOfTheMonth) {
		List<String> defaultTimeList = new ArrayList<String>();
		String work_start = laborService.getWorkStart(id);
		for (int i = 0; i < endOfTheMonth; i++) {
			if (workDivisionIdList.get(i) == 1) {
				defaultTimeList.add("00:00");
			} else {
				defaultTimeList.add(work_start);
			}
		}
		return defaultTimeList;
	}

	/**
	 * @param id
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @return
	 * 社員労務情報で設定した勤務終了時間のデフォルト値
	 */
	public List<String> getDefaultWorkEndList(Integer id, List<Integer> workDivisionIdList, int endOfTheMonth) {
		List<String> defaultTimeList = new ArrayList<String>();
		String work_end = laborService.getWorkEnd(id);
		for (int i = 0; i < endOfTheMonth; i++) {
			if (workDivisionIdList.get(i) == 1) {
				defaultTimeList.add("00:00");
			} else {
				defaultTimeList.add(work_end);
			}
		}
		return defaultTimeList;
	}

	/**
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @return
	 * 休憩時間のデフォルト値
	 */
	public List<Integer> getDefaultBreakTimeList(List<Integer> workDivisionIdList, int endOfTheMonth) {
		List<Integer> defaultBreakTimeList = new ArrayList<Integer>();
		for (int i = 0; i < endOfTheMonth; i++) {
			if (workDivisionIdList.get(i) == 1) {
				defaultBreakTimeList.add(0);
			} else {
				defaultBreakTimeList.add(60);
			}
		}
		return defaultBreakTimeList;
	}

	/**
	 * @param endOfTheMonth
	 * @param currentYear
	 * @param currentMonthStr
	 * @return
	 * "yyyy-MM-dd"形式のリスト
	 */
	public List<String> getOneDayList(int endOfTheMonth, int currentYear, String currentMonthStr) {
		List<String> oneDayList = new ArrayList<String>();
		for (int i = 1; i <= endOfTheMonth; i++) {
			// 日にちを0埋め表記
			String dateStr = otherLogic.getZeroPadding(i);
			String oneDayStr = currentYear + "-" + currentMonthStr + "-" + dateStr;
			oneDayList.add(oneDayStr);
		}
		return oneDayList;
	}

	/**
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @return
	 */
	public List<String> getDayOfWeekList(List<String> oneDayList, int endOfTheMonth) {
		List<String> DayOfWeekList = new ArrayList<String>();
		for (int i = 0; i < endOfTheMonth; i++) {
			// 当月/i日にフォーマット
			LocalDate oneDayDate = LocalDate.parse(oneDayList.get(i));
			// oneDayの曜日のみ取得
			DateTimeFormatter formatterWeek = DateTimeFormatter.ofPattern("EEE");
			String dayOfWeek = formatterWeek.format(oneDayDate);
			DayOfWeekList.add(dayOfWeek);
		}
		return DayOfWeekList;
	}

	/**
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @param currentMonthInt
	 * @return
	 * "MM/dd(EEE)"形式のリスト
	 */
	public List<String> getFullDayList(List<String> oneDayList, int endOfTheMonth, int currentMonthInt) {
		List<String> fullDayList = new ArrayList<String>();
		List<String> dayOfWeekList = getDayOfWeekList(oneDayList, endOfTheMonth);
		for (int i = 0; i < endOfTheMonth; i++) {
			String currentDate = String.valueOf(i + 1);
			String fullDay = currentMonthInt + "/" + currentDate + "日(" + dayOfWeekList.get(i) + ")";
			fullDayList.add(fullDay);
		}
		return fullDayList;
	}

	/**
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @return
	 * fullDayのid属性に付与する値(yyyyMMdd)
	 */
	public List<String> getOneDayIdList(List<String> oneDayList, int endOfTheMonth) {
		List<String> oneDayIdList = new ArrayList<String>();
		for (int i = 0; i <endOfTheMonth; i++) {
			String oneDayId = oneDayList.get(i).replace("-", "");
			oneDayIdList.add(oneDayId);
		}
		return oneDayIdList;
	}

	/**
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param endOfTheMonth
	 * @return
	 * 実働時間(分)のリスト
	 */
	public List<Integer> getActualWorkMinuteList(List<String> workStartTimeList, List<String> workEndTimeList, List<Integer> breakTimeList, int endOfTheMonth) {
		List<Integer> actualWorkMinuteList = new ArrayList<Integer>();
		for (int i = 0; i < endOfTheMonth; i++) {
			// 実働時間の計算
			int startHour = Integer.parseInt(workStartTimeList.get(i).substring(0,2));
			int startMinute = Integer.parseInt(workStartTimeList.get(i).substring(3));
			int endHour = Integer.parseInt(workEndTimeList.get(i).substring(0,2));
			int endMinute = Integer.parseInt(workEndTimeList.get(i).substring(3));
			int startTime = startHour * 60 + startMinute;
			int endTime = endHour * 60 + endMinute;
			int breaktime = breakTimeList.get(i);
			int actualMinute = 0;

			if (startTime < endTime) {
				actualMinute = endTime - startTime;
			} else if (startTime > endTime) {
				actualMinute = 24 * 60 - (startTime - endTime);
			}
			actualMinute -= breaktime;
			if (actualMinute < 0) {
				actualMinute = 0;
			}
			actualWorkMinuteList.add(actualMinute);
		}
		return actualWorkMinuteList;
	}

	/**
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param endOfTheMonth
	 * @return
	 * 実働時間のリスト("00:00"形式)
	 */
	public List<String> getActualWorkTimeList(List<String> workStartTimeList, List<String> workEndTimeList, List<Integer> breakTimeList, int endOfTheMonth) {
		List<String> actualWorkTimeList = new ArrayList<String>();
		List<Integer> actualMinuteList = getActualWorkMinuteList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		for (int i = 0; i < endOfTheMonth; i++) {
			int actualMinute = actualMinuteList.get(i);
			String actualWorkTime = otherLogic.timeFormatChange(actualMinute);
			actualWorkTimeList.add(actualWorkTime);
		}
		return actualWorkTimeList;
	}

	/**
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param endOfTheMonth
	 * @return
	 * 残業時間のリスト("00:00"形式)
	 */
	public List<String> getOverTimeList(List<String> workStartTimeList, List<String> workEndTimeList, List<Integer> breakTimeList, int endOfTheMonth) {
		List<Integer> actualWorkMinuteList = getActualWorkMinuteList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		List<String> overTimeList = new ArrayList<String>();
		for (int i = 0; i < actualWorkMinuteList.size(); i++) {
			if (480 < actualWorkMinuteList.get(i)) {
				int overTimeTotalMinute = actualWorkMinuteList.get(i) - 480;
				String overTime = otherLogic.timeFormatChange(overTimeTotalMinute);
				overTimeList.add(overTime);
			} else {
				overTimeList.add("0:00");
			}
		}
		return overTimeList;
	}

	/**
	 * @param fullDayList
	 * @param endOfTheMonth
	 * @return
	 * 曜日ごとに付与するclassのリスト
	 */
	public List<String> getDayOfWeekAddClass(List<String> fullDayList, int endOfTheMonth) {
		List<String> dayOfWeekList = new ArrayList<String>();
		for (int i = 0; i < endOfTheMonth; i++) {
			String s = fullDayList.get(i).substring(fullDayList.get(i).length() -3);
			if (s.equals("(土)")) {
				dayOfWeekList.add("saturday");
			} else if (s.equals("(日)")) {
				dayOfWeekList.add("sunday");
			} else {
				dayOfWeekList.add("weekday");
			}
		}
		return dayOfWeekList;
	}

	// 給与明細のロジック
	/**
	 * @param id
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 出勤日数(半休=0.5)
	 */
	public double getWorkingDays(Integer id, List<String> oneDayList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> workDivisionList = attendanceService.getWorkDivisionIdList(id, oneDayList, endOfTheMonth, begin, end, tableName);
		double count = 0;
		for (int i = 0; i < workDivisionList.size(); i++) {
			if (workDivisionList.get(i) == 2) {
				count++;
			}
			if (workDivisionList.get(i) == 4) {
				count += 0.5;
			}
		}
		return count;
	}

	/**
	 * @param id
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 有給休暇取得日数(半休=0.5)
	 */
	public double getPaidDays(Integer id, List<String> oneDayList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> workDivisionList = attendanceService.getWorkDivisionIdList(id, oneDayList, endOfTheMonth, begin, end, tableName);
		double count = 0;
		for (int i = 0; i < workDivisionList.size(); i++) {
			if (workDivisionList.get(i) == 3) {
				count++;
			}
			if (workDivisionList.get(i) == 4) {
				count += 0.5;
			}
		}
		return count;
	}

	/**
	 * @param id
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 欠勤日数
	 */
	public int getAbsenceDays(Integer id, List<String> oneDayList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> workDivisionList = attendanceService.getWorkDivisionIdList(id, oneDayList, endOfTheMonth, begin, end, tableName);
		int count = 0;
		for (int i = 0; i < workDivisionList.size(); i++) {
			if (workDivisionList.get(i) == 6) {
				count++;
			}
		}
		return count;
	}

	/**
	 * @param id
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 合計実働時間("00:00"形式)
	 */
	public String getTotalActualWorkTime(Integer id, List<String> workStartTimeList, List<String> workEndTimeList, List<Integer> breakTimeList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> actualWorkMinuteList = getActualWorkMinuteList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		int totalActualWorkMinute = 0;
		for (int i = 0; i < actualWorkMinuteList.size(); i++) {
			totalActualWorkMinute += actualWorkMinuteList.get(i);
		}
		String totalActualWorkTime = otherLogic.timeFormatChange(totalActualWorkMinute);
		return totalActualWorkTime;
	}

	/**
	 * @param id
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 合計残業時間("00:00"形式)
	 */
	public String getTotalOverTime(Integer id, List<String> workStartTimeList, List<String> workEndTimeList, List<Integer> breakTimeList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> actualMinuteList = getActualWorkMinuteList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		int totalOverTimeMinute = 0;
		for (int i = 0; i < actualMinuteList.size(); i++) {
			if (actualMinuteList.get(i) > 480) {
				totalOverTimeMinute += actualMinuteList.get(i) - 480;
			}
		}
		String totalOverTime = otherLogic.timeFormatChange(totalOverTimeMinute);
		return totalOverTime;
	}

//	// 深夜時間(未実装)
//	public String getNightShiftTime(Integer id) {
//		return "";
//	}
}
