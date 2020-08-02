package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.entity.factory.EmployeeEntityFactory;
import com.example.demo.entity.factory.UserEntityFactory;
import com.example.demo.mapper.UsersMapper;

@Service
public class UserService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UserEntityFactory userEntityFactory;
	@Autowired
	private EmployeeEntityFactory employeeEntityFactory;
	@Autowired
    PasswordEncoder passwordEncoder;

	/**
	 * @param user
	 */
	public void registUser(UserDto user) {
		// パスワードを暗号化してDBに登録
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		usersMapper.regist(userEntityFactory.create(user));
	}

	/**
	 * @param email
	 * @return
	 */
	public User findUserByEmail(String email) {
		return usersMapper.findUserByEmail(email);
	}

	/**
	 * @param id
	 * @return
	 */
	public User getUserById(Integer id) {
		return usersMapper.getUserById(id);
	}

	/**
	 * @param email
	 * @param password
	 */
	public void accountEdit(String email, String password) {
		// パスワードを暗号化してDBに登録
		password = passwordEncoder.encode(password);
		usersMapper.accountEdit(email, password);
	}

	/**
	 * @param email
	 * @return
	 */
	public Employee getEmployeeByEmail(String email) {
		return usersMapper.getEmployeeByEmail(email);
	}

	/**
	 * @param employee
	 */
	public void employeeEdit(EmployeeDto employee) {
		usersMapper.employeeEdit(employeeEntityFactory.edit(employee));
	}

	/**
	 * @return
	 */
	public List<User> getEmployeeList() {
		return usersMapper.getEmployeeList();
	}
}
