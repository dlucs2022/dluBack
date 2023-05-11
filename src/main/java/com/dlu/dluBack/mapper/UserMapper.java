package com.dlu.dluBack.mapper;

import com.dlu.dluBack.bean.User;
import com.dlu.dluBack.bean.Wait_register;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User getUser(@Param("name") String name,@Param("password") String password);
    Integer queryInviteCode(@Param("code") String code);

    Integer queryUserName(@Param("name") String name);

    List<Wait_register> checkList(@Param("code") String invite_code);

    int queryUserNameFromWait(@Param("name") String name);

    void passUserCheck(@Param("name") String name);

    void update_passUserCheck(@Param("name") String name);

    User queryUserByName(@Param("name") String name);

    void update_refuseCheck(@Param("name") String name);

    void deleteUserByName(@Param("name") String name);

    void multiPass(@Param("list") List<String> list);

    String getPassword(@Param("name") String name);

    void updatePassword(@Param("name")String name,@Param("password") String password);

    void addUser(Wait_register r);
}
