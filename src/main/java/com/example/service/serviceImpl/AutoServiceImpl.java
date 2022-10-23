package com.example.service.serviceImpl;

import com.example.entity.AuthUser;
import com.example.mapper.UserMapper;
import com.example.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Slf4j
@Service
public class AutoServiceImpl implements AutoService {

    @Resource
    UserMapper mapper;

    @Transactional
    @Override
    public void register(String username, String password, String sex, String grade) throws Exception {
        log.info("开始注册事务");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthUser user = new AuthUser(0, username, encoder.encode(password), "user");
        if (mapper.registerUser(user) <= 0) {
            throw new RuntimeException("用户信息添加失败");
        }
        log.info("用户信息添加成功");
        if (mapper.addStudentInfo(username, user.getId(), grade, sex) <= 0) {
            throw new RuntimeException("学生信息添加失败");
        }
        log.info("学生用户信息添加成功");
    }
}
