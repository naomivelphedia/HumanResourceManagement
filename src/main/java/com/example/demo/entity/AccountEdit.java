package com.example.demo.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AccountEdit {
	private Integer id;
	private String email;
	private String old_password;
	private String password;
	private String confirm_password;
}
