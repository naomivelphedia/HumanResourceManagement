package com.example.demo.entity.factory;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Attendance;

@Component
public class AttendanceEntityFactory {

	/**
	 * @param user_id
	 * @param date_id
	 * @param work_division_id
	 * @param work_start
	 * @param work_end
	 * @param break_time
	 * @param remarks
	 * @param approval
	 * @return
	 */
	public Attendance upsert(Integer user_id, Integer date_id, Integer work_division_id, String work_start, String work_end, Integer break_time, String remarks, String approval) {
		return new Attendance(
				user_id,
				date_id,
				work_division_id,
				work_start,
				work_end,
				break_time,
				remarks,
				approval
		);
	}
}
