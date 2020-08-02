$(function() {
    // 現在の年月日を取得
    const now = new Date();
    const current_year = now.getFullYear();
    const current_month = now.getMonth() + 1;
    const current_date = now.getDate();

    // selectで選択された年月日を取得
    let selected_birth_year = document.getElementById("birth_year").value;
    let selected_birth_month = document.getElementById("birth_month").value;

    // 年(1900〜現在の年)の値を設定
    for (let i = current_year; i >= 1900 ; i--) {
        $('#birth_year').append('<option value="' + i + '">' + i + '</option>');
    }
    // 月(1~12)の値を設定
    for (let j = 1; j <= 12; j++) {
        $('#birth_month').append('<option value="' + j + '">' + j + '</option>');
    }
    // 日(1~31)の値を設定
    for (let k = 1; k <= 31; k++) {
        $('#birth_date').append('<option value="' + k + '">' + k + '</option>');
    }

    // 選択された年に合わせて、適した月の値を選択肢にセットする
    $('#birth_year').change(function() {
        selected_birth_year = $('#birth_year').val();
        // 現在の年が選択された場合、月の選択肢は 1~現在の月に設定
        // それ以外の場合、1~12 に設定
        let last_month = 12;
        if (selected_birth_year == current_year) {
            last_month = current_month;
        }
        $('#birth_month').children('option').remove();
        $('#birth_month').append('<option value="">--</option>');
        for (let n = 1; n <= last_month; n++) {
            $('#birth_month').append('<option value="' + n + '">' + n + '</option>');
        }
    });

    // 選択された年・月に合わせて、適した日の値を選択肢にセットする
    $('#birth_year,#birth_month').change(function() {
        selected_birth_year = $('#birth_year').val();
        selected_birth_month = $('#birth_month').val();
        let last_date = 0;
        // 現在の年・月が選択された場合、日の選択肢は 1~現在の日付 に設定
        // それ以外の場合、各月ごとの月末日を判定し、1~月末日 に設定
        if (selected_birth_year == current_year && selected_birth_month == current_month ) {
        	last_date = current_date;
        } else {
            // 2月の選択肢は1~28日に設定 ※ ただし、閏年の場合は29日に設定
            if (selected_birth_month == 2) {
                if ((Math.floor(selected_birth_year % 4 == 0)) && (Math.floor(selected_birth_year % 100 != 0)) || (Math.floor(selected_birth_year % 400 == 0))) {
                    last_date = 29;
                } else {
                    last_date = 28;
                }
            // 4, 6, 9, 11月の日の選択肢は1~30日に設定
            } else if (selected_birth_month == 4 || selected_birth_month == 6 || selected_birth_month == 9 || selected_birth_month == 11 ) {
                last_date = 30;
            // 1, 3, 5, 7, 8, 10, 12月の日の選択肢は1~31日に設定
            } else {
                last_date = 31;
            }
        }
        $('#birth_date').children('option').remove();
        $('#birth_date').append('<option value="">--</option>');
        for (let m = 1; m <= last_date; m++) {
            $('#birth_date').append('<option value="' + m + '">' + m + '</option>');
        }
    });



    // selectで選択された年月日を取得
    let selected_hire_year = document.getElementById("hire_year").value;
    let selected_hire_month = document.getElementById("hire_month").value;

    // 年(1900〜現在の年)の値を設定
    for (let i = current_year; i >= 1900 ; i--) {
        $('#hire_year').append('<option value="' + i + '">' + i + '</option>');
    }
    // 月(1~12)の値を設定
    for (let j = 1; j <= 12; j++) {
        $('#hire_month').append('<option value="' + j + '">' + j + '</option>');
    }
    // 日(1~31)の値を設定
    for (let k = 1; k <= 31; k++) {
        $('#hire_date').append('<option value="' + k + '">' + k + '</option>');
    }

    // 選択された年に合わせて、適した月の値を選択肢にセットする
    $('#hire_year').change(function() {
        selected_hire_year = $('#hire_year').val();
        // 現在の年が選択された場合、月の選択肢は 1~現在の月 に設定
        // それ以外の場合、1~12 に設定
        let last_month = 12;
        if (selected_hire_year == current_year) {
            last_month = current_month;
        }
        $('#hire_month').children('option').remove();
        $('#hire_month').append('<option value="">--</option>');
        for (let n = 1; n <= last_month; n++) {
            $('#hire_month').append('<option value="' + n + '">' + n + '</option>');
        }
    });

    // 選択された年・月に合わせて、適した日の値を選択肢にセットする
    $('#hire_year,#hire_month').change(function() {
        selected_hire_year = $('#hire_year').val();
        selected_hire_month = $('#hire_month').val();
        let last_date = 0;
        // 現在の年・月が選択された場合、日の選択肢は 1~現在の日付 に設定
        // それ以外の場合、各月ごとの月末日を判定し、1~月末日 に設定
        if (selected_hire_year == current_year && selected_hire_month == current_month ) {
            last_date = current_date;
        } else {
            // 2月の選択肢は1~28日に設定 ※ ただし、閏年の場合は29日に設定
            if (selected_hire_month == 2) {
                if ((Math.floor(selected_hire_year % 4 == 0)) && (Math.floor(selected_hire_year % 100 != 0)) || (Math.floor(selected_hire_year % 400 == 0))) {
                    last_date = 29;
                } else {
                    last_date = 28;
                }
            // 4, 6, 9, 11月の日の選択肢は1~30日に設定
            } else if (selected_hire_month == 4 || selected_hire_month == 6 || selected_hire_month == 9 || selected_hire_month == 11 ) {
                last_date = 30;
            // 1, 3, 5, 7, 8, 10, 12月の日の選択肢は1~31日に設定
            } else {
                last_date = 31;
            }
        }
        $('#hire_date').children('option').remove();
        $('#hire_date').append('<option value="">--</option>');
        for (let m = 1; m <= last_date; m++) {
            $('#hire_date').append('<option value="' + m + '">' + m + '</option>');
        }
    });
});