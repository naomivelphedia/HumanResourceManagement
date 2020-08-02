package com.example.demo.dto.factory;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.LoginUser;
import com.example.demo.form.SignupForm;

@Component
public class UserDtoFactory {

	/**
	 * @param form
	 * @return
	 */
	public UserDto create(SignupForm form) {
		Date birth_day = Date.valueOf(form.getBirth_year() + "-" + form.getBirth_month() + "-" + form.getBirth_date());
		Date hire_day = Date.valueOf(form.getHire_year() + "-" + form.getHire_month() + "-" + form.getHire_date());
		return new UserDto(
				null,
				form.getName(),
				birth_day,
				form.getGender(),
				form.getZip3() + form.getZip4(),
				form.getAddress(),
				form.getTelnumber(),
				form.getEmail(),
				form.getPassword(),
				form.getEmployment_status(),
				hire_day,
				form.getRole(),
				form.getEmg_name(),
				form.getRelation(),
				form.getEmg_zip3() + form.getEmg_zip4(),
				form.getEmg_address(),
				form.getEmg_telnumber()
		);
	}

	/**
	 * @param loginUser
	 * @return
	 */
	public UserDto find(LoginUser loginUser) {
		return new UserDto(
				loginUser.getId(),
				loginUser.getName(),
				loginUser.getBirth_day(),
				loginUser.getGender(),
				loginUser.getZipcode(),
				loginUser.getAddress(),
				loginUser.getTelnumber(),
				loginUser.getEmail(),
				loginUser.getPassword(),
				loginUser.getEmployment_status(),
				loginUser.getHire_day(),
				loginUser.getRole(),
				loginUser.getEmg_name(),
				loginUser.getRelation(),
				loginUser.getEmg_zipcode(),
				loginUser.getEmg_address(),
				loginUser.getEmg_telnumber()
		);
	}
}
