package com.example.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, name FROM users WHERE id = #{id}")
    User findById(long id);

    @Select("SELECT id, name FROM users ORDER BY id")
    List<User> findAll();

    @Insert("INSERT INTO users(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
}
