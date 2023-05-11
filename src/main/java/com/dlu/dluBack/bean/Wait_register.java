package com.dlu.dluBack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wait_register {
    private String name;
    private String password;
//    private String power;
    private String invite_code;
    private String create_time;
    private String status;
//    private String del;
}
