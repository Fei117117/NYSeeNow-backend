package com.group13.nyseenowbackend.mapper;

import com.group13.nyseenowbackend.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM \"UserAccount\" WHERE username = #{text}")
    Account findAccountByName(String text);

    @Select("SELECT * FROM \"UserAccount\" WHERE email = #{email}")
    Account findAccountByEmail(String email);

    @Insert("insert into \"UserAccount\" (username, password, email) values (#{username}, #{password}, #{email})")
    int creatAccount(String username, String password, String email);

    @Update("UPDATE \"UserAccount\" SET username=#{username}, password=#{password} WHERE email=#{email}")
    int updateAccount(Account account);

}
