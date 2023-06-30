package com.group13.nyseenowbackend.mapper;

import com.group13.nyseenowbackend.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

// Interface for User database operations
@Mapper
public interface UserMapper {

    // Query to find an account by name
    @Select("SELECT * FROM \"UserAccount\" WHERE username = #{text}")
    Account findAccountByName(String text);

    // Query to find an account by email
    @Select("SELECT * FROM \"UserAccount\" WHERE email = #{email}")
    Account findAccountByEmail(String email);

    // Insert a new account into the UserAccount table
    @Insert("insert into \"UserAccount\" (username, password, email) values (#{username}, #{password}, #{email})")
    int creatAccount(String username, String password, String email);

    // Update existing account details in the UserAccount table
    @Update("UPDATE \"UserAccount\" SET username=#{username}, password=#{password} WHERE email=#{email}")
    int updateAccount(Account account);

}
