package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AccountEditDto;
import com.example.demo.entity.AccountEdit;

@Component
public class AccountEditEntityFactory {

	/**
	 * @param dto
	 * @return
	 */
	public AccountEdit accountEdit(AccountEditDto dto) {
		return new AccountEdit(
				dto.getId(),
				dto.getEmail(),
				dto.getOld_password(),
				dto.getPassword(),
				dto.getConfirm_password()
		);
	}
}
