package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.UserDtoFactory;
import com.example.demo.form.SignupForm;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("signup")
public class SignupController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDtoFactory userDtoFactory;

	/**
	 * @param mav
	 * @param signupForm
	 * @return
	 */
	@GetMapping
	public ModelAndView signupPage(ModelAndView mav, SignupForm signupForm) {
        return mav;
	}

	/**
	 * @param mav
	 * @param signupForm
	 * @return
	 */
	@PostMapping
	public ModelAndView signupPost(ModelAndView mav, SignupForm signupForm) {
		userService.registUser(userDtoFactory.create(signupForm));
		mav.setViewName("redirect:top");
		return mav;
	}
}
