$(function () {
	$('.menu').children('label').click(function() {
		if ($(this).attr('class') == 'selected') {
			// メニュー非表示
			$(this).removeClass('selected').next('ul').slideUp('fast');
		} else {
			// 表示しているメニューを閉じる
			$('.menu').children('label').removeClass('selected');
			$('.menu').children('ul').hide();
			// メニュー表示
			$(this).addClass('selected').next('ul').slideDown('fast');
		}
	});

	// マウスカーソルがメニュー上/メニュー外
	var over_flg = true;
	$('.menu').children('label,ul').hover(
		function() {
			over_flg = true;
		},
		function() {
			over_flg = false;
		});
		// メニュー領域外をクリックしたらメニューを閉じる
		$('body').click(function() {
		if (over_flg == false) {
			$('.menu').children('label').removeClass('selected');
			$('.menu').children('ul').slideUp('fast');
		}
	});
});