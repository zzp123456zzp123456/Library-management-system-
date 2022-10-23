package com.example.mapper;

import com.example.entity.AuthUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    AuthUser getPasswordByUsername(String username);

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into users(username,password,role) values(#{username},#{password},#{role})")
    int registerUser(AuthUser user);

    @Insert("insert into students(uid,sex,grade,name) values(#{uid},#{sex},#{grade},#{username})")
    int addStudentInfo(@Param("username") String username, @Param("uid") int uid, @Param("grade") String grade, @Param("sex") String sex);

    @Select("select sid from students where uid=#{id}")
    Integer getSidByUserId(int id);

    @Select("select count(*) from students")
    int getStudentCount();

}
