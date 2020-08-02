package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.form.ProfileForm;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	private UserService userService;
//	@Autowired
//	private OtherService otherService;

	/**
	 * @param loginUser
	 * @param user
	 * @param profileForm
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView profilePage(@AuthenticationPrincipal LoginUser loginUser, User user, ProfileForm profileForm, ModelAndView mav) {
		user = userService.getUserById(loginUser.getId());
		mav.addObject("loginUser", user);
		return mav;
	}

	/**
	 * @param loginUser
	 * @param user
	 * @param profileForm
	 * @param mav
	 * @return
	 * プロフィール画像の登録(未実装)
	 */
	@PostMapping
	public ModelAndView profileEditPost(@AuthenticationPrincipal LoginUser loginUser, User user, ProfileForm profileForm, ModelAndView mav) {
		// 仮実装
//		List<Byte> files = new List<Byte>();
//		files.setFile(file.getBytes());
//		otherService.uploadImage(files);
//		otherService.uploadImage(profileForm.getImage());
		return mav;
	}
}