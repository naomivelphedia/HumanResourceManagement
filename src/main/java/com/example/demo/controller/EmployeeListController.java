package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.logic.ProfileValueLogic;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("employees")
public class EmployeeListController {

	@Autowired
	private UserService userService;
	@Autowired
	private ProfileValueLogic profileValueLogic;

	/**
	 * @param loginUser
	 * @param user
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView employeeListPage(@AuthenticationPrincipal LoginUser loginUser, User user, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
		List<User> employees = userService.getEmployeeList();
		mav.addObject("employees", employees);
		// 社員全員の誕生日をフォーマット
		List<Date> birthDayList = employees.stream()
                .map(s -> s.getBirth_day())
                .collect(Collectors.toList());
		List<Integer> ageList = new ArrayList<Integer>();
		for (int i = 0; i < birthDayList.size(); i++) {
			int age = profileValueLogic.getAge(birthDayList.get(i));
			ageList.add(age);
		}
		mav.addObject("ageList", ageList);
        return mav;
	}
}
