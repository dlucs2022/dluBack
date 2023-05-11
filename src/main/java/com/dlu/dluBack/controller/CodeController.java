package com.dlu.dluBack.controller;

import com.dlu.dluBack.commons.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@CrossOrigin(origins = "*" ,maxAge = 3600)
public class CodeController {
    @RequestMapping(path = "/code" ,method = RequestMethod.GET)
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建验证码
        VerifyCode verifyCode = new VerifyCode();

        BufferedImage image = verifyCode.createImage();
        int code = verifyCode.getResult();
        request.getSession().setAttribute("code",String.valueOf(code));

        //写出验证码
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image,"jpeg",outputStream);
        System.out.println(String.valueOf(code));

    }
}
