package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.EmployeeDtoFactory;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.EmployeeInfoForm;
import com.example.demo.logic.ProfileValueLogic;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("employeeInfo")
public class EmployeeInfoController {

	@Autowired
	private UserService userService;
	@Autowired
	private EmployeeDtoFactory employeeDtoFactory;
	@Autowired
	private ProfileValueLogic profileValueLogic;

	/**
	 * @param email
	 * @param loginUser
	 * @param employee
	 * @param employeeInfoForm
	 * @param user
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView employeeInfoPage(@RequestParam("email")String email, @AuthenticationPrincipal LoginUser loginUser, Employee employee, EmployeeInfoForm employeeInfoForm, User user, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
		employee = userService.getEmployeeByEmail(email);
		mav.addObject("employee", employee);
		String prev_zip = profileValueLogic.getPrevZip(employee.getZipcode());
		mav.addObject("prev_zip", prev_zip);
		String next_zip = profileValueLogic.getNextZip(employee.getZipcode());
		mav.addObject("next_zip", next_zip);
		String emg_prev_zip = profileValueLogic.getPrevZip(employee.getEmg_zipcode());
		mav.addObject("emg_prev_zip", emg_prev_zip);
		String emg_next_zip = profileValueLogic.getNextZip(employee.getEmg_zipcode());
		mav.addObject("emg_next_zip", emg_next_zip);
		return mav;
	}

	/**
	 * @param loginUser
	 * @param employeeInfoForm
	 * @param mav
	 * @return
	 */
	@PostMapping
	public ModelAndView employeeInfoPost(@AuthenticationPrincipal LoginUser loginUser, EmployeeInfoForm employeeInfoForm, ModelAndView mav) {
		userService.employeeEdit(employeeDtoFactory.employeeEdit(employeeInfoForm));
		mav.setViewName("redirect:employees");
		return mav;
	}
}
