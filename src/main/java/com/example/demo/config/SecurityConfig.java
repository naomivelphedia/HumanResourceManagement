package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 *ログイン認証のフィルターにかけないファイル群をパスで指定
	 */
	@Override
    public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/bootstrap/css/**", "/bootstrap/js/**", "/jquery/**", "/img/**", "/fonts/**");
    }

	/**
	 *ログイン認証およびログアウト時の処理
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// セキュリティ認証管理
			.authorizeRequests()
				.antMatchers("/login", "/signup", "/passReset").permitAll() // 認証なしでアクセス可能
				.antMatchers("/regist", "/employeeInfoEdit").hasRole("ADMIN") // 管理者ユーザのみ許可
				.anyRequest()
				.authenticated()
				.and()
			// 認証ページ処理
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password")
				// ログイン失敗時に入力した値の保持
				.failureHandler((req, res, exp) -> {
					res.sendRedirect("/login?error=true&email=" + req.getParameter("email"));
				})
				.defaultSuccessUrl("/top", true)
				.permitAll()
				.and()
			// ログアウト処理
			.logout()
				.logoutSuccessUrl("/login")
				.deleteCookies("JSESSIONID", "SESSION")
				.invalidateHttpSession(true)
				.permitAll();
	}

    /**
     * @return
     * パスワードのハッシュ化メソッド
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *ハッシュ化されたパスワードをuserDetailsServiceで使用
     */
    @Override
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }
}