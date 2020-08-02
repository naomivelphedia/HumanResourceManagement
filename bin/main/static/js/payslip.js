$(function() {
	const basic_salary = Number($('#basic_salary').text()).toLocaleString();
	$('#basic_salary').text(basic_salary);
	const pass_price = Number($('#pass_price').text()).toLocaleString();
	$('#pass_price').text(pass_price);
});