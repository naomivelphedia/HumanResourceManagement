package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEditForm {
	private Integer id;
	private String email;
	private String old_password;
	private String password;
	private String confirm_password;
}
