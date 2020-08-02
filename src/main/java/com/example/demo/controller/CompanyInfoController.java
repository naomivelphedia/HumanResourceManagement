package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.CompanyInfoDtoFactory;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.CompanyInfoForm;
import com.example.demo.service.CompanyService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("companyInfo")
public class CompanyInfoController {

	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyInfoDtoFactory companyInfoDtoFactory;

	/**
	 * @param loginUser
	 * @param user
	 * @param companyInfoForm
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView companyInfoPage(@AuthenticationPrincipal LoginUser loginUser, User user, CompanyInfoForm companyInfoForm, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
        return mav;
	}

	/**
	 * @param loginUser
	 * @param mav
	 * @param companyInfoForm
	 * @return
	 */
	@PostMapping
	public ModelAndView companyInfoPost(@AuthenticationPrincipal LoginUser loginUser, ModelAndView mav, CompanyInfoForm companyInfoForm) {
		companyService.upsertCompanyInfo(companyInfoDtoFactory.upsert(companyInfoForm));
		mav.setViewName("redirect:top");
		return mav;
	}
}
