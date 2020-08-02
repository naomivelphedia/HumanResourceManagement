package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEditDto {
	private Integer id;
	private String email;
	private String old_password;
	private String password;
	private String confirm_password;
}
