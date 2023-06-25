package com.group13.nyseenowbackend.mapper;

import com.group13.nyseenowbackend.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM \"UserAccount\" WHERE username = #{text} OR email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Insert("insert into \"UserAccount\" (username, password, email) values (#{username}, #{password}, #{email})")
    int creatAccount(String username, String password, String email);
}
