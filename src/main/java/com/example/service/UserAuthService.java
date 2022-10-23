package com.example.service;

import com.example.entity.AuthUser;
import com.example.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //从数据库根据用户名获取密码
        AuthUser user = mapper.getPasswordByUsername(s);
        if(user == null){ throw new UsernameNotFoundException("登录失败，用户名或密码错误！");}
        return User   //这里需要返回UserDetails，SpringSecurity会根据给定的信息进行比对
                .withUsername(s)
                .password(user.getPassword())   //直接从数据库取的密码
                .roles(user.getRole())   //用户角色
                .build();

    }
}
