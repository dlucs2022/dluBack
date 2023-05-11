package com.dlu.dluBack.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {
    private static final Integer SUCCESS_CODE =200;
    private static final Integer FAILED_CODE = 201;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILED_MSG = "failed";


    private Integer code;
    private String message;
    private Object data;

    public static ResultVo success(Object data){
        return new ResultVo(SUCCESS_CODE,SUCCESS_MSG,data);
    }
    public static ResultVo failed(){
        return new ResultVo(FAILED_CODE,FAILED_MSG,"");
    }

    public static ResultVo failed(String failedmsg){
        return new ResultVo(FAILED_CODE,failedmsg,"");
    }
}
