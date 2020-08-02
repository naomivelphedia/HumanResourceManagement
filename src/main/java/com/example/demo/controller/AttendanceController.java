package com.example.demo.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.AttendanceDtoFactory;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.AttendanceForm;
import com.example.demo.logic.AttendanceTableLogic;
import com.example.demo.logic.OtherLogic;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("attendance")
public class AttendanceController {

	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AttendanceDtoFactory attendanceDtoFactory;
	@Autowired
	private AttendanceTableLogic attendanceTableLogic;
	@Autowired
	private OtherLogic otherLogic;

	/**
	 * @param dateValue
	 * @param currentYearMonth
	 * @param email
	 * @param loginUser
	 * @param user
	 * @param attendance
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView attendancePage(@RequestParam(name = "dateValue", defaultValue = "") String dateValue,
																	@RequestParam(name = "currentYearMonth", defaultValue = "") String currentYearMonth,
																	@RequestParam(name = "email", defaultValue = "") String email,
																	@AuthenticationPrincipal LoginUser loginUser, User user, Attendance attendance, ModelAndView mav) {
		if (!email.equals("")) {
			user = userService.findUserByEmail(email);
		} else {
			user = userService.getUserById(loginUser.getId());
		}

		currentYearMonth = otherLogic.getCurrentYearMonth(currentYearMonth, dateValue);

		// テーブルの存在確認(なければテーブル作成)
		String tableName = "attendance" + currentYearMonth;
		if (attendanceTableLogic.tableExists(tableName) && attendanceTableLogic.isWithin3yearsAgo(currentYearMonth)) {
			attendanceService.createTable(tableName, currentYearMonth);
			// 3年前の勤怠表はテーブル作成と同時に削除
			String deleteTableName = attendanceTableLogic.getDeleteTableName();
			if (!attendanceTableLogic.tableExists(deleteTableName)) {
				attendanceService.deleteTable(deleteTableName);
			}
		}

		int currentYear = otherLogic.getYear(currentYearMonth);
		int currentMonthInt = otherLogic.getMonthInt(currentYearMonth);
		String currentMonthStr = otherLogic.getMonthStr(currentYearMonth);
		int endOfTheMonth = LocalDate.of(currentYear, currentMonthInt, 1).lengthOfMonth();
		int begin = Integer.parseInt(String.valueOf(currentYear) + currentMonthStr + "01");
		int end = Integer.parseInt(String.valueOf(currentYear) + currentMonthStr + String.valueOf(endOfTheMonth));

		// 現在日時から3年前の勤怠表まで前月、翌月ボタンを配置
		boolean isPrevbtn = attendanceTableLogic.isPrevBtn(currentYearMonth);
		boolean isNextbtn = attendanceTableLogic.isFuture(currentYearMonth);

		// DBに登録済の勤務時間表
		List<String> oneDayList = attendanceTableLogic.getOneDayList(endOfTheMonth, currentYear, currentMonthStr);
		List<String> fullDayList = attendanceTableLogic.getFullDayList(oneDayList, endOfTheMonth, currentMonthInt);
		List<String> oneDayIdList = attendanceTableLogic.getOneDayIdList(oneDayList, endOfTheMonth);
		List<String> dayOfWeekAddClass = attendanceTableLogic.getDayOfWeekAddClass(fullDayList, endOfTheMonth);
		List<Integer> workDivisionSelectId = attendanceService.getWorkDivisionSelectId();
		List<String> workDivisionName = attendanceService.getWorkDivisionName();
		List<String> workDivisionAddclass = attendanceService.getWorkDivisionAddclass();
		List<Integer> workDivisionIdList = attendanceService.getWorkDivisionIdList(user.getId(), oneDayList, endOfTheMonth, begin, end, tableName);
		List<String> workStartTimeList = attendanceService.getWorkStartTimeList(user.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		List<String> workEndTimeList = attendanceService.getWorkEndTimeList(user.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		List<Integer> breakTimeList = attendanceService.getBreakTimeList(user.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		List<String> actualWorkTimeList = attendanceTableLogic.getActualWorkTimeList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		List<String> overTimeList = attendanceTableLogic.getOverTimeList(workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth);
		String[] remarksList = attendanceService.getRemarksList(user.getId(), endOfTheMonth, begin, end, tableName);
		List<String> approvalList = attendanceService.getApprovalList(user.getId(), endOfTheMonth, begin, end, tableName);

		mav.addObject("user", user);
		mav.addObject("loginUser", loginUser);
		mav.addObject("currentYearMonth", currentYearMonth);
		mav.addObject("endOfTheMonth", endOfTheMonth);
		mav.addObject("isPrevbtn", isPrevbtn);
		mav.addObject("isNextbtn", isNextbtn);
		mav.addObject("fullDayList", fullDayList);
		mav.addObject("oneDayIdList", oneDayIdList);
		mav.addObject("dayOfWeekAddClass", dayOfWeekAddClass);
		mav.addObject("workDivisionSelectId", workDivisionSelectId);
		mav.addObject("workDivisionName", workDivisionName);
		mav.addObject("workDivisionAddclass", workDivisionAddclass);
		mav.addObject("workDivisionIdList", workDivisionIdList);
		mav.addObject("workStartTimeList", workStartTimeList);
		mav.addObject("workEndTimeList", workEndTimeList);
		mav.addObject("breakTimeList", breakTimeList);
		mav.addObject("actualWorkTimeList", actualWorkTimeList);
		mav.addObject("overTimeList", overTimeList);
		mav.addObject("remarksList", remarksList);
		mav.addObject("approvalList", approvalList);

        return mav;
	}

	/**
	 * @param currentYearMonth
	 * @param mav
	 * @param attendanceForm
	 * @return
	 */
	@PostMapping
	public ModelAndView attendancePost(@RequestParam(name = "currentYearMonth") String currentYearMonth,
																	ModelAndView mav,
																	AttendanceForm attendanceForm) {
		int currentYear = otherLogic.getYear(currentYearMonth);
		int currentMonthInt = otherLogic.getMonthInt(currentYearMonth);
		String tableName = "attendance" + currentYearMonth;
		int endOfTheMonth = LocalDate.of(currentYear, currentMonthInt, 1).lengthOfMonth();
		attendanceService.upsertAttendance(attendanceDtoFactory.upsert(attendanceForm), tableName, endOfTheMonth);
		mav.setViewName("redirect:top");
		return mav;
	}

	/**
	 * @param response
	 * @param currentYearMonth
	 * @param endOfTheMonth
	 * @param fullDayList
	 * @param workDivisionIdList
	 * @param workStartTimeList
	 * @param workEndTimeList
	 * @param breakTimeList
	 * @param actualWorkTimeList
	 * CSV形式でファイル出力するメソッド
	 */
	@RequestMapping("/csv")
	@GetMapping
	public void csvDownload(HttpServletResponse response,
													@RequestParam(name = "currentYearMonth") String currentYearMonth,
													@RequestParam(name = "endOfTheMonth") int endOfTheMonth,
													@RequestParam(name = "fullDayList") List<String> fullDayList,
													@RequestParam(name = "workDivisionIdList") List<String> workDivisionIdList,
													@RequestParam(name = "workStartTimeList") List<String> workStartTimeList,
													@RequestParam(name = "workEndTimeList") List<String> workEndTimeList,
													@RequestParam(name = "breakTimeList") List<String> breakTimeList,
													@RequestParam(name = "actualWorkTimeList") List<String> actualWorkTimeList) {
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"attendance"+currentYearMonth+".csv\"");

		try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(response.getWriter()));
            pw.print("日付,出勤区分,出勤時間,退勤時間,休憩時間,");
        	pw.println("実働時間");
            for (int i = 0; i < endOfTheMonth; i++) {
            	pw.print(fullDayList.get(i));
            	pw.print(",");
            	pw.print(workDivisionIdList.get(i));
            	pw.print(",");
            	pw.print(workStartTimeList.get(i));
            	pw.print(",");
            	pw.print(workEndTimeList.get(i));
            	pw.print(",");
            	pw.print(breakTimeList.get(i));
            	pw.print(",");
            	pw.println(actualWorkTimeList.get(i));
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
