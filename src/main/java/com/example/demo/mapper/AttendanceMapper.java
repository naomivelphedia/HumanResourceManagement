package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Attendance;

@Repository
@Mapper
public interface AttendanceMapper {
	String findTable(String tableName);
	void createTable(@Param("tableName") String tableName, @Param("currentYearMonth") String currentYearMonth);
	void deleteTable(@Param("tableName") String tableName);
	void upsert(@Param("attendance") Attendance attendance, @Param("tableName") String tableName);
	Attendance getUserAttendance(Integer id);
	List<String> getWorkStart(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
	List<String> getWorkEnd(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
	List<Integer> getBreakTime(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
	List<Integer> getWorkDivisionSelectId();
	List<String> getWorkDivisionName();
	List<String> getWorkDivisionAddclass();
	List<Integer> getWorkDivisionIdList(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
	String[] getRemarks(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
	List<String> getApproval(@Param("id") Integer id, @Param("begin") Integer begin, @Param("end") Integer end, @Param("tableName") String tableName);
}
