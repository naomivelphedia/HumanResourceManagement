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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginUser;
import com.example.demo.logic.AttendanceTableLogic;
import com.example.demo.logic.OtherLogic;
import com.example.demo.logic.PayslipTableLogic;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.LaborService;

@RestController
@RequestMapping("payslip")
public class PayslipController {

	@Autowired
	private AttendanceTableLogic attendanceTableLogic;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private LaborService laborService;
	@Autowired
	private PayslipTableLogic payslipTableLogic;
	@Autowired
	private OtherLogic otherLogic;

	/**
	 * @param dateValue
	 * @param currentYearMonth
	 * @param loginUser
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView payslipPage(@RequestParam(name = "dateValue", defaultValue = "") String dateValue,
															@RequestParam(name = "currentYearMonth", defaultValue = "") String currentYearMonth,
															@AuthenticationPrincipal LoginUser loginUser, ModelAndView mav) {
		mav.addObject("loginUser", loginUser);
		int basic_salary = laborService.getBasic_salary(loginUser.getId());
		int hourly_wage = laborService.getHourly_wage(loginUser.getId());
		String fixed_overtime = laborService.getFixed_overtime(loginUser.getId());
		int pass_price = laborService.getPass_price(loginUser.getId());

		currentYearMonth = otherLogic.getCurrentYearMonth(currentYearMonth, dateValue);

		int currentYear = otherLogic.getYear(currentYearMonth);
		int currentMonthInt = otherLogic.getMonthInt(currentYearMonth);
		String currentYearMonthStr = otherLogic.getMonthStr(currentYearMonth);
		String currentMonthStr = otherLogic.getMonthStr(currentYearMonth);
		int endOfTheMonth = LocalDate.of(currentYear, currentMonthInt, 1).lengthOfMonth();
		int begin = Integer.parseInt(String.valueOf(currentYear) + currentYearMonthStr + "01");
		int end = Integer.parseInt(String.valueOf(currentYear) + currentYearMonthStr + String.valueOf(endOfTheMonth));

		// 勤怠テーブルが存在しなかったらerrorメッセージを表示
		String tableName = "attendance" + currentYearMonth;
		if (attendanceTableLogic.tableExists(tableName)) {
			mav.addObject("error", "明細表がありません");
			mav.addObject("currentYearMonth", currentYearMonth);
			return mav;
		}
		// 勤怠が全て承認されていなければerrorメッセージを表示
		List<String> approvalList = attendanceService.getApprovalList(loginUser.getId(), endOfTheMonth, begin, end, tableName);
		if (approvalList.contains("off")) {
			mav.addObject("error", "明細表がありません");
			mav.addObject("currentYearMonth", currentYearMonth);
			return mav;
		}

		List<String> oneDayList = attendanceTableLogic.getOneDayList(endOfTheMonth, currentYear, currentMonthStr);
		List<Integer> workDivisionIdList = attendanceService.getWorkDivisionIdList(loginUser.getId(), oneDayList, endOfTheMonth, begin, end, tableName);
		double workingDays = attendanceTableLogic.getWorkingDays(loginUser.getId(), oneDayList, endOfTheMonth, begin, end, tableName);
		double paidDays = attendanceTableLogic.getPaidDays(loginUser.getId(), oneDayList, endOfTheMonth, begin, end, tableName);
		int absenceDays = attendanceTableLogic.getAbsenceDays(loginUser.getId(), oneDayList, endOfTheMonth, begin, end, tableName);
		List<String> workStartTimeList = attendanceService.getWorkStartTimeList(loginUser.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		List<String> workEndTimeList = attendanceService.getWorkEndTimeList(loginUser.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		List<Integer> breakTimeList = attendanceService.getBreakTimeList(loginUser.getId(), workDivisionIdList, endOfTheMonth, begin, end, tableName);
		String totalActualWorkTime = attendanceTableLogic.getTotalActualWorkTime(loginUser.getId(), workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth, begin, end, tableName);
		String totalOverTime = attendanceTableLogic.getTotalOverTime(loginUser.getId(),workStartTimeList, workEndTimeList, breakTimeList, endOfTheMonth, begin, end, tableName);
		int overtimePay = payslipTableLogic.getOvertimePay(totalOverTime);

		mav.addObject("basic_salary", basic_salary);
		mav.addObject("hourly_wage", hourly_wage);
		mav.addObject("fixed_overtime", fixed_overtime);
		mav.addObject("pass_price", pass_price);
		mav.addObject("currentYearMonth", currentYearMonth);
		mav.addObject("endOfTheMonth", endOfTheMonth);
		mav.addObject("workingDays", workingDays);
		mav.addObject("paidDays", paidDays);
		mav.addObject("absenceDays", absenceDays);
		mav.addObject("totalActualWorkTime", totalActualWorkTime);
		mav.addObject("totalOverTime", totalOverTime);
		mav.addObject("overtimePay", overtimePay);

        return mav;
	}

	/**
	 * @param response
	 * @param currentYearMonth
	 * @param workingDays
	 * @param paidDays
	 * @param absenceDays
	 * @param totalActualWorkTime
	 * @param totalOverTime
	 * @param fixed_overtime
	 * @param basic_salary
	 * @param pass_price
	 * CSV形式でファイル出力するメソッド
	 */
	@RequestMapping("/csv")
	@GetMapping
	public void csvDownload(HttpServletResponse response,
													@RequestParam(name = "currentYearMonth") String currentYearMonth,
													@RequestParam(name = "workingDays") String workingDays,
													@RequestParam(name = "paidDays") String paidDays,
													@RequestParam(name = "absenceDays") String absenceDays,
													@RequestParam(name = "totalActualWorkTime") String totalActualWorkTime,
													@RequestParam(name = "totalOverTime") String totalOverTime,
													@RequestParam(name = "fixed_overtime") String fixed_overtime,
													@RequestParam(name = "basic_salary") String basic_salary,
													@RequestParam(name = "pass_price") String pass_price) {
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"payslip"+currentYearMonth+".csv\"");

		try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(response.getWriter()));
            pw.print("出勤日数,有給取得日数,欠勤日数,総実働時間,総残業時間,固定残業代,基本給,");
        	pw.println("通勤手当て");
        	pw.print(workingDays);
        	pw.print(",");
        	pw.print(paidDays);
        	pw.print(",");
        	pw.print(absenceDays);
        	pw.print(",");
        	pw.print(totalActualWorkTime);
        	pw.print(",");
        	pw.print(totalOverTime);
        	pw.print(",");
        	pw.print(fixed_overtime);
        	pw.print(",");
        	pw.print(basic_salary);
        	pw.print(",");
        	pw.println(pass_price);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
