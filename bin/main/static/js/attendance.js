$(function() {
//	const now = new Date();
//	const currentYear = now.getFullYear();
//	const currentMonth = now.getMonth() + 1;
//	// 月を0埋め
//	const  zeroMonth = ('0' + currentMonth).slice(-2);
//	// 当月末取得
//	const endOfTheMonth = new Date(now.setMonth(currentMonth));
//	endOfTheMonth.setDate(0);
//	// 曜日リスト
//	const dayOfWeekList = [ '日', '月', '火', '水', '木', '金', '土' ];
//
//	for (let i = 1; i <= endOfTheMonth.getDate(); i++) {
//		const oneDay = new Date(currentYear + '-' + currentMonth + '-' + i);
//		// fullDayHtmlのidに付与する値(yyyymmdd)
//		const  zeroDate = ('0'+ i).slice(-2);
//		const oneDayId = currentYear + zeroMonth + zeroDate;
//		// 曜日の取得
//		const dayOfWeekIndex = oneDay.getDay();
//		const dayOfWeek = dayOfWeekList[dayOfWeekIndex];
//		// mm月dd日(E)の形式
//		const fullDay = currentMonth + '月' + i + '日' + '(' + dayOfWeek + ')';
//		const fullDayHtml = '<tr id=' + oneDayId + ' class=day' + i + '><td id=' + oneDayId + ' class=day' + i + '>' + fullDay + '</td></tr>';
//		// 勤務区分のセレクトボタン
//		const workCategorySelect = "<select id='" + oneDayId + " work-category-select' class='" + oneDayId + " work-category-select'>" +
//																"<option value='出勤'>出勤</option>" +
//																"<option value='休み'>休み</option>" +
//																"<option value='有給休暇'>有給休暇</option>" +
//														"</select>";
//		// td要素のテンプレート
//		const workDetailsHtml = '<td id=work-category' + i + ' class=work-category' + i + '>' + workCategorySelect + '</td>' +
//												'<td id=work-start' + i + ' class=work-start' + i + '>' + '<input type=time name=work_start th:value=${attendances.work_start} id=work-start-input' + i + '>' + '</td>' +
//												'<td id=work-end' + i + ' class=work-end' + i + '>' + '<input type=time name=work_end th:value=${attendances.work_end} id=work-end-input' + i + '>' + '</td>' +
//												'<td id=break-time' + i + ' class=break-time' + i + '>' + '<input type=time name=break_time th:value=${attendances.break_time} id=break-time-input' + i + '>' + '</td>' +
//												'<td id=actual-work' + i + ' class=actual-work' + i + '>' + '</td>';
//		// 土日と平日にそれぞれidを付与
//		if (dayOfWeek === '土') {
//			$('tbody').append(fullDayHtml)
//			$('#' + oneDayId).addClass('saturday').append(workDetailsHtml);
//			$('tr').children('#' + oneDayId).addClass('saturday');
//		} else if(dayOfWeek === '日') {
//			$('tbody').append(fullDayHtml);
//			$('#' + oneDayId).addClass('sunday').append(workDetailsHtml);
//			$('tr').children('#' + oneDayId).addClass('sunday');
//		} else {
//			$('tbody').append(fullDayHtml);
//			$('#' + oneDayId).addClass('weekday').append(workDetailsHtml);
//			$('tr').children('#' + oneDayId).addClass('weekday');
//		}
//	}

	const now = new Date();
	const now_year = now.getFullYear();
	const now_month = now.getMonth() + 1;
	for (let i = now_year; i >= now_year - 3 ; i--) {
		$('#select_year').append('<option value="' + i + '">' + i + '</option>');
	}
	for (let j = 1; j <= 12; j++) {
		$('#select_month').append('<option value="' + j + '">' + j + '</option>');
	}
	// 選択された年に合わせて、適した月の値を選択肢にセットする
	$('#select_year').change(function() {
		selected_select_year = $('#select_year').val();
		// 現在の年、もしくは3年前が選択された場合、月の選択肢は 1~現在の月に設定
		// それ以外の場合、1~12 に設定
		let first_month = 1;
		let last_month = 12;
		if (selected_select_year == now_year) {
			last_month = now_month;
		} else if (selected_select_year == now_year - 3) {
			first_month = now_month;
		}
		$('#select_month').children('option').remove();
		$('#select_month').append('<option value="">--</option>');
		for (let n = first_month; n <= last_month; n++) {
			$('#select_month').append('<option value="' + n + '">' + n + '</option>');
		}
	});

	// 出勤退勤の打刻
	function timeStamp(work) {
		const now = new Date();
		const currentHour = ('0' + now.getHours()).slice(-2);
		const currentMinute = ('0' + now.getMinutes()).slice(-2);
		const currentTime = currentHour + ':' + currentMinute;
		const today = now.getDate();
		console.log(work + today);
		return $(work + today).val(currentTime);
	}
	$('#work-start-btn').on('click', () => {
		timeStamp('.work-start-time');
	});
	$('#work-end-btn').on('click', () => {
		timeStamp('.work-end-time');
	});

	// 承認ボタン
	$('.approval').on('change', (e) => {
		let checkId = $(e.target.closest('tr'));
		if ($(e.target).prop('checked')) {
			$(checkId).find('.hidden-approval').prop('disabled', true);
		} else {
			$(checkId).find('.hidden-approval').prop('disabled', false);
		}
	});

	// 承認ボタンの全選択,解除
	$('#approval-all').on('change', () => {
		if ($('#approval-all').prop('checked')) {
			$('.approval').prop('checked', true);
			$('.hidden-approval').prop('disabled', true);
		} else {
			console.log('false');
			$('.approval').prop('checked', false);
			$('.hidden-approval').prop('disabled', false);
		}
	});
});