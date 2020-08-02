package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.AccountEditForm;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("accountEdit")
public class AccountEditController {

	@Autowired
	private UserService userService;
	@Autowired
    PasswordEncoder passwordEncoder;

	/**
	 * @param error
	 * @param loginUser
	 * @param user
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView accountEditPage(@RequestParam(name = "error", defaultValue = "") String error, @AuthenticationPrincipal LoginUser loginUser, User user, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
		mav.addObject("error", error);
		return mav;
	}

	/**
	 * @param loginUser
	 * @param user
	 * @param accountEditForm
	 * @param mav
	 * @return
	 */
	@PostMapping
	public ModelAndView accountEditPost(@AuthenticationPrincipal LoginUser loginUser, User user, AccountEditForm accountEditForm, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);

		if (!passwordEncoder.matches(accountEditForm.getOld_password(), user.getPassword())) {
			mav.addObject("error", "パスワードが間違っています");
			mav.setViewName("redirect:accountEdit");
			return mav;
		} else {
			userService.accountEdit(user.getEmail(), accountEditForm.getPassword());
			mav.setViewName("redirect:top");
			return mav;
		}
	}
}
