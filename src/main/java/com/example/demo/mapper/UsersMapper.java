package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.User;

@Repository
@Mapper
public interface UsersMapper {
	void regist(User user);
	User findUserByEmail(String email);
	User getUserById(Integer id);
	void accountEdit(@Param("email") String email, @Param("password") String password);
	List<User> getEmployeeList();
	Employee getEmployeeByEmail(String email);
	void employeeEdit(Employee employee);
}