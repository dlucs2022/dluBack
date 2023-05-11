package com.dlu.dluBack.service.impl;

import com.dlu.dluBack.bean.User;
import com.dlu.dluBack.bean.Wait_register;
import com.dlu.dluBack.mapper.UserMapper;
import com.dlu.dluBack.mapper.Wait_registerMapper;
import com.dlu.dluBack.service.UserService;
import com.dlu.dluBack.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public User getUser(String name, String password) {
        return userMapper.getUser(name,password);
    }

    @Autowired
    Wait_registerMapper w;

    @Override
    public void add_wait_register(Wait_register r) {
        if("".equals(r.getInvite_code())){
            //没有邀请码 直接注册
            userMapper.addUser(r);
        }else{
            r.setStatus("待审核");
            w.add_wait_register(r);
        }

    }

    @Override
    public Integer queryInviteCode(String code) {
        return userMapper.queryInviteCode(code);
    }

    @Override
    public Integer queryUserName(String name) {
        return userMapper.queryUserName(name);
    }

    @Override
    public PageVo<Wait_register> check_list(int pageNum, int pageSize, String invite_code) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wait_register> waitList = userMapper.checkList(invite_code);
        waitList.forEach(wait_register -> wait_register.setCreate_time(
                wait_register.getCreate_time().substring(
                        0,wait_register.getCreate_time().length()-2
                )
        ));
        waitList.forEach(wait_register -> wait_register.setPassword(""));
        PageInfo<Wait_register> pageInfo = new PageInfo<>(waitList);
        PageVo<Wait_register> res = new PageVo<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),
                pageInfo.getPages(),waitList);
        return res;
    }



    @Override
    public int queryUserNameFromWait(String name) {
        return userMapper.queryUserNameFromWait(name);
    }

    @Transactional  //当前方法开启事务管理
    @Override
    public void passUserCheck(String name) {
        userMapper.passUserCheck(name);
        userMapper.update_passUserCheck(name);
    }
    @Transactional
    @Override
    public void refuseCheck(String name) {
        //先看user表中他是不是正式用户
        User u = userMapper.queryUserByName(name);
        if (u == null){
            //不是正式用户，则只改变wait表
            userMapper.update_refuseCheck(name);
        }else {
            //是正式用户，则都要修改
            userMapper.deleteUserByName(name);
            userMapper.update_refuseCheck(name);
        }
    }

    @Transactional
    @Override
    public void multiPass(String[] list) {
        for(String i : list){
            this.passUserCheck(i);
        }

    }

    @Override
    public void multiRefuse(String[] list) {
        for (String s : list) {
            this.refuseCheck(s);
        }
    }

    @Override
    public String getPassword(String name) {
        return userMapper.getPassword(name);
    }

    @Override
    public void updatePassword(String name, String password) {
        userMapper.updatePassword(name,password);
    }


}
