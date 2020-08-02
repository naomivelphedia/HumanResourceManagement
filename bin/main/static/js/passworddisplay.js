$(function() {
	// パスワードを表示する機能
	$('#passcheck').change(function(){
		if ($(this).prop('checked')) {
			$('#password').attr('type','text');
			$('#old_password').attr('type','text');
			$('#confirm_password').attr('type','text');
		} else {
			$('#password').attr('type','password');
			$('#old_password').attr('type','password');
			$('#confirm_password').attr('type','password');
		}
	});
});