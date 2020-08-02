package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

@Component
public class UserEntityFactory {

	/**
	 * @param dto
	 * @return
	 */
	public User create(UserDto dto) {
		return new User(
				null,
				dto.getName(),
				dto.getBirth_day(),
				dto.getGender(),
				dto.getZipcode(),
				dto.getAddress(),
				dto.getTelnumber(),
				dto.getEmail(),
				dto.getPassword(),
				dto.getEmployment_status(),
				dto.getHire_day(),
				dto.getRole(),
				dto.getEmg_name(),
				dto.getRelation(),
				dto.getEmg_zipcode(),
				dto.getEmg_address(),
				dto.getEmg_telnumber()
		);
	}


	/**
	 * @param dto
	 * @return
	 */
	public User find(UserDto dto) {
		return new User(
				dto.getId(),
				dto.getName(),
				dto.getBirth_day(),
				dto.getGender(),
				dto.getZipcode(),
				dto.getAddress(),
				dto.getTelnumber(),
				dto.getEmail(),
				dto.getPassword(),
				dto.getEmployment_status(),
				dto.getHire_day(),
				dto.getRole(),
				dto.getEmg_name(),
				dto.getRelation(),
				dto.getEmg_zipcode(),
				dto.getEmg_address(),
				dto.getEmg_telnumber()
		);
	}

	/**
	 * @param dto
	 * @return
	 */
	public User edit(UserDto dto) {
		return new User(
				dto.getId(),
				dto.getName(),
				dto.getBirth_day(),
				dto.getGender(),
				dto.getZipcode(),
				dto.getAddress(),
				dto.getTelnumber(),
				dto.getEmail(),
				dto.getPassword(),
				dto.getEmployment_status(),
				dto.getHire_day(),
				dto.getRole(),
				dto.getEmg_name(),
				dto.getRelation(),
				dto.getEmg_zipcode(),
				dto.getEmg_address(),
				dto.getEmg_telnumber()
		);
	}
}
