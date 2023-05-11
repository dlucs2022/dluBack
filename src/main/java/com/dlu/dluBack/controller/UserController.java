package com.dlu.dluBack.controller;

import com.dlu.dluBack.bean.User;
import com.dlu.dluBack.bean.Wait_register;
import com.dlu.dluBack.service.UserService;
import com.dlu.dluBack.utils.DateUtil;
import com.dlu.dluBack.vo.PageVo;
import com.dlu.dluBack.vo.ResultVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登录
     * @param request
     * @param name
     * @param password
     * @param code
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public ResultVo login(HttpServletRequest request,
                          @RequestParam String name,
                          @RequestParam String password,
                          @RequestParam String code){
        //比较验证码
        HttpSession session = request.getSession();
        String sessionCode =(String) session.getAttribute("code");


        if(StringUtils.equals(sessionCode,code)){
            System.out.println(session.getId());
            System.out.println(code);
            return ResultVo.failed("验证码错误");
        }
        //查询数据库
        User user = userService.getUser(name, password);
        if (user == null){
            return ResultVo.failed("用户名或密码错误");
        }else {
            user.setPassword("");
            log.info(user.getName()+"login:"+ DateUtil.DateToString(new Date()));
            return ResultVo.success(user);
        }

    }

    /**
     * 跳转到登录页面
     * @return
     */
    @ResponseBody
    @PostMapping("/toLogin")
    public String toLogin(){
        System.out.println(userService.getUser("test","123456"));
        return "login";
    }

    /**
     * 检查用户名是否存在
     * @param name
     * @return
     */
    @ResponseBody
    @PostMapping("/check_name")
    public ResultVo check_name(String name){
        int res_num = userService.queryUserName(name);
        int res_num2 = userService.queryUserNameFromWait(name);
        if (res_num>0||res_num2>0){
            return ResultVo.failed("failed");
        }else {
            return ResultVo.success("success"); //可以注册
        }
    }

    /**
     * 检查邀请码是否存在
     *@POST请求，检查邀请码是否存在
     * @param code
     * @return
     */
    @ResponseBody
    @PostMapping("/be_invited")
    public ResultVo be_invited(String code){
        int res_num = userService.queryInviteCode(code);
        if (res_num>0){
            return ResultVo.success("success");//已有邀请码，可以注册
        }else {
            return ResultVo.failed("failed");   //没有此邀请码
        }
    }

    /**
     * 注册
     * @param wait_register
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public ResultVo register(@RequestBody Wait_register wait_register){
        userService.add_wait_register(wait_register);
        return ResultVo.success("success");
    }

    //审核列表
    @ResponseBody
    @PostMapping("/checkList")
    public ResultVo checkList(String invite_code,@RequestParam(required = false,defaultValue = "1") int pageNumber,
                                @RequestParam(required = false,defaultValue = "5") int pageSize){

        int pageNum = pageNumber;

        PageVo<Wait_register> wait_registers = userService.check_list(pageNum,pageSize,invite_code);

        return ResultVo.success(wait_registers);
    }

    //审核通过:将用户放入正式用户组,审核表中状态改变
    @ResponseBody
    @PostMapping("/passCheck")
    public ResultVo passCheck(String name){
        userService.passUserCheck(name);
        return ResultVo.success("success");
    }

    //审核拒绝，包括将已通过的拒绝和未通过的拒绝
    @ResponseBody
    @PostMapping("/refuse")
    public ResultVo refuseCheck(String name){
        userService.refuseCheck(name);
        return ResultVo.success("success");
    }


    //批量通过
    @ResponseBody
    @PostMapping("/multiPass")
    public ResultVo multiPass(@RequestParam(name = "list" , defaultValue = "") String[] list){
        userService.multiPass(list);
        return ResultVo.success("success");
    }

    //批量拒绝
    @ResponseBody
    @PostMapping("/multiRefuse")
    public ResultVo multiRefuse(@RequestParam(name = "list" , defaultValue = "") String[] list){
        userService.multiRefuse(list);
        return ResultVo.success("success");
    }

    //获取某用户密码 以修改密码
    @ResponseBody
    @PostMapping("/getPassword")
    public ResultVo getPassword(String name){
        String password = userService.getPassword(name);
        return ResultVo.success(password);
    }


    //更改某用户密码
    @ResponseBody
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(String name,String password){
        userService.updatePassword(name,password);
        return ResultVo.success("success");
    }

}
