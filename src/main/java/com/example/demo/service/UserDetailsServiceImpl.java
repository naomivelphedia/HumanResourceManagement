package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginUser;
import com.example.demo.entity.User;
import com.example.demo.mapper.UsersMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /** DBから情報を得るためのリポジトリ */

    @Autowired
    private UsersMapper usersMapper;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)DBから検索をし、ログイン情報を構成(権限も付与)して返す
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.equals("")) {
            throw new UsernameNotFoundException("emailまたはパスワードが間違っています。");
        }
        User user = usersMapper.findUserByEmail(email);

        // ユーザおよび管理者権限の付与
        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
          if(user.getRole() == 1) {
              authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
          }
          LoginUser loginUser = new LoginUser(user, authorityList);
        return loginUser;
    }
}