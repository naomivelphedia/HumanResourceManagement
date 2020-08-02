package com.example.demo.dto.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AccountEditDto;
import com.example.demo.form.AccountEditForm;

@Component
public class AccountEditDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public AccountEditDto accountEdit(AccountEditForm form) {
		return new AccountEditDto(
			form.getId(),
			form.getEmail(),
			form.getOld_password(),
			form.getPassword(),
			form.getConfirm_password()
		);
	}
}
