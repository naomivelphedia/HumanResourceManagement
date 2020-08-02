package com.example.demo.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("passReset")
public class PassResetController {

	@Autowired
	private UserService userService;
	@Autowired
	private MailSender mailSender;

	/**
	 * @param mav
	 * @return
	 */
	@GetMapping
	public ModelAndView passResetPage(ModelAndView mav) {
		return mav;
	}

	/**
	 * @param email
	 * @param user
	 * @param mav
	 * @return
	 */
	@PostMapping
	public ModelAndView passResetSend(@RequestParam(name = "email") String email,
																User user,
																ModelAndView mav) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        user = userService.findUserByEmail(email);
        // user(アカウント)が存在しなければ、その旨をメール本文に記載して送信
        if (user == null) {
        	simpleMailMessage.setTo(email);
        	simpleMailMessage.setSubject("パスワード再設定のお知らせ");
            simpleMailMessage.setText("人材管理サービスです\n\r\n\r" + email + "\n\r\n\rこちらのアカウントは存在しません");
            this.mailSender.send(simpleMailMessage);
			mav.setViewName("redirect:login");
			return mav;
        }
        String password = RandomStringUtils.randomAscii(8);;
        userService.accountEdit(user.getEmail(), password);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("パスワード再設定のお知らせ");
        simpleMailMessage.setText("人材管理サービスです\n\r\n\r" + email + "\n\r\n\rこちらのアカウントのパスワードを再設定しました。\n\r\n\r再設定パスワード：" + password + "\n\r\n\rログイン後、パスワードをご自身で再設定してください。");
        this.mailSender.send(simpleMailMessage);
        mav.setViewName("redirect:login");
		return mav;
	}
}
