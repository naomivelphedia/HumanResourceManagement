$(function() {
	// 日(1~31)の値を設定
	for (let i = 1; i <= 31; i++) {
        $('#cutoff_date').append('<option value="' + i + '">' + i + '</option>');
    }
});