package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/signup").setViewName("signup");
		registry.addViewController("/passReset").setViewName("passReset");
		registry.addViewController("/").setViewName("top");
		registry.addViewController("/top").setViewName("top");
		registry.addViewController("/accountEdit").setViewName("accountEdit");
		registry.addViewController("/apply").setViewName("apply");
		registry.addViewController("/attendance").setViewName("attendance");
		registry.addViewController("/companyInfo").setViewName("companyInfo");
		registry.addViewController("/employeeInfo").setViewName("employeeInfo");
		registry.addViewController("/employeeLabor").setViewName("employeeLabor");
		registry.addViewController("/employees").setViewName("employees");
		registry.addViewController("/payslip").setViewName("payslip");
		registry.addViewController("/profile").setViewName("profile");
		registry.addViewController("/regist").setViewName("regist");
	}
}