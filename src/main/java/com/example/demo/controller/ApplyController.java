package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("apply")
public class ApplyController {

	@Autowired
	private UserService userService;

	/**
	 * @param loginUser
	 * @param user
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView applyPage(@AuthenticationPrincipal LoginUser loginUser, User user, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
        return mav;
	}
}
