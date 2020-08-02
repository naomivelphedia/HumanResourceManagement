package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AttendanceDto;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.factory.AttendanceEntityFactory;
import com.example.demo.logic.AttendanceTableLogic;
import com.example.demo.mapper.AttendanceMapper;

@Service
public class AttendanceService {

	@Autowired
	private AttendanceMapper attendanceMapper;
	@Autowired
	private AttendanceEntityFactory attendanceEntityFactory;
	@Autowired
	private AttendanceTableLogic attendanceTableLogic;

	/**
	 * @param tableName
	 * @return
	 */
	public String findTable(String tableName) {
		return attendanceMapper.findTable(tableName);
	}

	/**
	 * @param tableName
	 * @param currentYearMonth
	 */
	public void createTable(String tableName, String currentYearMonth) {
		attendanceMapper.createTable(tableName, currentYearMonth);
	}

	/**
	 * @param tableName
	 */
	public void deleteTable(String tableName) {
		attendanceMapper.deleteTable(tableName);
	}

	/**
	 * @param attendance
	 * @param tableName
	 * @param endOfTheMonth
	 * 勤怠表の登録及び更新
	 */
	public void upsertAttendance(AttendanceDto attendance, String tableName, int endOfTheMonth) {
		// date_idのリスト
		String[] dateIdStrList = attendance.getDate_id().split(",");
		List<Integer> dateIdList =
		          Arrays
		            .stream(dateIdStrList)
		            .map(Integer::valueOf)
		            .collect(Collectors.toList());
		// work_divisionのリスト
		String[] workDivisionStrList = attendance.getWork_division_id().split(",");
		List<Integer> workDivisionIdList =
				Arrays
					.stream(workDivisionStrList)
					.map(Integer::valueOf)
					.collect(Collectors.toList());
		// work_startのリスト
		List<String> workStartTimeList = new ArrayList<String>(Arrays.asList(attendance.getWork_start().split(",")));
		// work_endのリスト
		List<String> workEndTimeList = new ArrayList<String>(Arrays.asList(attendance.getWork_end().split(",")));
		// break_timeのリスト
		String[] breakTimeStrList = attendance.getBreak_time().split(",");
		List<Integer> breakTimeList =
				Arrays
					.stream(breakTimeStrList)
					.map(Integer::valueOf)
					.collect(Collectors.toList());
		// remaksのリスト
		String[] remarksList = new String[endOfTheMonth];
		remarksList = attendance.getRemarks().split(",", -1);
		if (remarksList == null || remarksList.length == 0) {
			remarksList = new String[endOfTheMonth];
		}
		// approvalのリスト
		List<String> approvalList = new ArrayList<String>(Arrays.asList(attendance.getApproval().split(",")));

		for (int i = 0; i < endOfTheMonth; i++) {
			attendanceMapper.upsert(attendanceEntityFactory.upsert(attendance.getUser_id(), dateIdList.get(i), workDivisionIdList.get(i), workStartTimeList.get(i), workEndTimeList.get(i), breakTimeList.get(i), remarksList[i], approvalList.get(i)), tableName);
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public Attendance getUserAttendance(Integer id) {
		return attendanceMapper.getUserAttendance(id);
	}

	/**
	 * @param id
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 */
	public List<String> getWorkStartTimeList(Integer id, List<Integer> workDivisionIdList, int endOfTheMonth, int begin, int end, String tableName) {
		List<String> workStartTimeList = attendanceMapper.getWorkStart(id, begin, end, tableName);
		if (workStartTimeList == null || workStartTimeList.size() == 0) {
			workStartTimeList = attendanceTableLogic.getDefaultWorkStartList(id, workDivisionIdList, endOfTheMonth);
		}
		return workStartTimeList;
	}

	/**
	 * @param id
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 */
	public List<String> getWorkEndTimeList(Integer id, List<Integer> workDivisionIdList, int endOfTheMonth, int begin, int end, String tableName) {
		List<String> workEndTimeList = attendanceMapper.getWorkEnd(id, begin, end, tableName);
		if (workEndTimeList == null || workEndTimeList.size() == 0) {
			workEndTimeList = attendanceTableLogic.getDefaultWorkEndList(id, workDivisionIdList, endOfTheMonth);
		}
		return workEndTimeList;
	}

	/**
	 * @param id
	 * @param workDivisionIdList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 */
	public List<Integer> getBreakTimeList(Integer id, List<Integer> workDivisionIdList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> breakTimeList = attendanceMapper.getBreakTime(id, begin, end, tableName);
		if (breakTimeList == null || breakTimeList.size() == 0) {
			breakTimeList = attendanceTableLogic.getDefaultBreakTimeList(workDivisionIdList, endOfTheMonth);
		}
		return breakTimeList;
	}

	/**
	 * @return
	 * 出勤区分セレクタのid
	 */
	public List<Integer> getWorkDivisionSelectId() {
		return attendanceMapper.getWorkDivisionSelectId();
	}

	/**
	 * @return
	 * 出勤区分セレクタのテキスト
	 */
	public List<String> getWorkDivisionName() {
		return attendanceMapper.getWorkDivisionName();
	}

	/**
	 * @return
	 * 出勤区分セレクタに付与するclass名
	 */
	public List<String> getWorkDivisionAddclass() {
		return attendanceMapper.getWorkDivisionAddclass();
	}

	/**
	 * @param id
	 * @param oneDayList
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * ユーザの勤怠に登録された出勤区分のリスト
	 */
	public List<Integer> getWorkDivisionIdList(Integer id, List<String> oneDayList, int endOfTheMonth, int begin, int end, String tableName) {
		List<Integer> workDivisionIdList = attendanceMapper.getWorkDivisionIdList(id, begin, end, tableName);
		if (workDivisionIdList == null || workDivisionIdList.size() == 0) {
			workDivisionIdList = attendanceTableLogic.getDefaultDivisionIdList(id, oneDayList, endOfTheMonth);
		}
		return workDivisionIdList;
	}

	/**
	 * @param id
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 備考欄の配列(null許容)
	 */
	public String[] getRemarksList(Integer id, int endOfTheMonth, int begin, int end, String tableName) {
		String[] remarksList = attendanceMapper.getRemarks(id, begin, end, tableName);
		if (remarksList == null || remarksList.length == 0) {
			remarksList = new String[endOfTheMonth];
		}
		return remarksList;
	}

	/**
	 * @param id
	 * @param endOfTheMonth
	 * @param begin
	 * @param end
	 * @param tableName
	 * @return
	 * 承認ボタンのon,offのリスト
	 */
	public List<String> getApprovalList(Integer id, int endOfTheMonth, int begin, int end, String tableName) {
		List<String> approvalList = attendanceMapper.getApproval(id, begin, end, tableName);
		if (approvalList == null || approvalList.size() == 0) {
			for (int i = 0; i < endOfTheMonth; i++) {
				approvalList.add("off");
			}
		}
		return approvalList;
	}
}
