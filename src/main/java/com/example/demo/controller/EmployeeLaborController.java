package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.LaborDtoFactory;
import com.example.demo.entity.Labor;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.EmployeeLaborForm;
import com.example.demo.logic.EmployeeLaborLogic;
import com.example.demo.service.LaborService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("employeeLabor")
public class EmployeeLaborController {

	@Autowired
	private UserService userService;
	@Autowired
	private LaborService laborService;
	@Autowired
	private LaborDtoFactory laborDtoFactory;
	@Autowired
	private EmployeeLaborLogic employeeLaborLogic;

	/**
	 * @param email
	 * @param loginUser
	 * @param user
	 * @param employeeLaborForm
	 * @param labor
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView employeeLaborPage(@RequestParam("email")String email, @AuthenticationPrincipal LoginUser loginUser, User user, EmployeeLaborForm employeeLaborForm, Labor labor, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
		user = userService.findUserByEmail(email);
		mav.addObject("employee", user);
		labor = employeeLaborLogic.getEmployeeLabor(user.getId());
		mav.addObject("employeeLabor", labor);

		return mav;
	}

	/**
	 * @param loginUser
	 * @param employeeLaborForm
	 * @param mav
	 * @return
	 */
	@PostMapping
	public ModelAndView employeeLaborPost(@AuthenticationPrincipal LoginUser loginUser, EmployeeLaborForm employeeLaborForm, ModelAndView mav) {
		laborService.upsertLabor(laborDtoFactory.upsert(employeeLaborForm));
		mav.setViewName("redirect:employees");
		return mav;
	}
}
