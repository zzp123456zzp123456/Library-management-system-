package com.example.service.serviceImpl;

import com.example.entity.GlobalStat;
import com.example.mapper.BookMapper;
import com.example.mapper.UserMapper;
import com.example.service.StatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class StatServiceImpl implements StatService {
    @Resource
    BookMapper bookMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public GlobalStat getGlobalStat() {
        return new GlobalStat(userMapper.getStudentCount(),
                bookMapper.getBookCount(),
                bookMapper.getBorrowCount());
    }

}
