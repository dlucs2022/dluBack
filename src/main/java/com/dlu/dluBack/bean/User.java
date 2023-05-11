package com.dlu.dluBack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
//    private String id;
    private String name;
    private String password;
    private String power;
    private String invite_code;
    private String create_time;
}
