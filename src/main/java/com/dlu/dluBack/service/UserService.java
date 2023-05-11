package com.dlu.dluBack.service;


import com.dlu.dluBack.bean.User;
import com.dlu.dluBack.bean.Wait_register;
import com.dlu.dluBack.vo.PageVo;

import java.util.List;


public interface UserService {
    User getUser(String name, String password);
    void add_wait_register(Wait_register r);
    Integer queryInviteCode(String code);
    Integer queryUserName(String name);
    PageVo<Wait_register> check_list(int pageNum, int pageSize, String invite_code);

    int queryUserNameFromWait(String name);

    void passUserCheck(String name);

    void refuseCheck(String name);

    void multiPass(String[] list);

    void multiRefuse(String[] list);

    String getPassword(String name);

    void updatePassword(String name,String password);
}
