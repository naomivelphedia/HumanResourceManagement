package com.example.demo.entity;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class LoginUser implements UserDetails{
	private User user;
	private Collection<GrantedAuthority> authorities;

	public LoginUser() {
	}

	/**
	 * @param user
	 * @param authorities
	 */
	public LoginUser(User user, Collection<GrantedAuthority> authorities){
		this.user = user;
		this.authorities = authorities;
	}

	/**
	 *付与する権限のリスト
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * @return
	 */
	public Integer getId() {
		return user.getId();
	}

	/**
	 * @return
	 */
	public String getName() {
		return user.getName();
	}

	/**
	 * @return
	 */
	public Date getBirth_day() {
		return user.getBirth_day();
	}

	/**
	 * @return
	 */
	public String getGender() {
		return user.getGender();
	}

	/**
	 * @return
	 */
	public String getZipcode() {
		return user.getZipcode();
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return user.getAddress();
	}

	/**
	 * @return
	 */
	public String getTelnumber() {
		return user.getTelnumber();
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return user.getEmail();
	}

	/**
	 * @return
	 */
	public int getEmployment_status() {
		return user.getEmployment_status();
	}

	/**
	 * @return
	 */
	public Date getHire_day() {
		return user.getHire_day();
	}

	/**
	 * @return
	 */
	public int getRole() {
		return user.getRole();
	}

	/**
	 * @return
	 */
	public String getEmg_name() {
		return user.getEmg_name();
	}

	/**
	 * @return
	 */
	public String getRelation() {
		return user.getRelation();
	}

	/**
	 * @return
	 */
	public String getEmg_zipcode() {
		return user.getEmg_zipcode();
	}

	/**
	 * @return
	 */
	public String getEmg_address() {
		return user.getEmg_address();
	}

	/**
	 * @return
	 */
	public String getEmg_telnumber() {
		return user.getEmg_telnumber();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
