package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.factory.UserDtoFactory;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.SignupForm;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("regist")
public class RegistController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDtoFactory userDtoFactory;

	/**
	 * @param loginUser
	 * @param user
	 * @param mav
	 * @param signupForm
	 * @return
	 */
	@GetMapping
	public ModelAndView registPage(@AuthenticationPrincipal LoginUser loginUser, User user, ModelAndView mav, SignupForm signupForm) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
        return mav;
	}

	/**
	 * @param mav
	 * @param signupForm
	 * @return
	 */
	@PostMapping
	public ModelAndView registPost(ModelAndView mav, SignupForm signupForm) {
		userService.registUser(userDtoFactory.create(signupForm));
		mav.setViewName("redirect:top");
		return mav;
	}
}
