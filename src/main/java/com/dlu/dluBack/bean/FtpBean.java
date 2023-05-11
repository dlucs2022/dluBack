package com.dlu.dluBack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author hcf
 * @Date 2023/4/24 9:17
 * @Description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtpBean {

    private String server_file_path;
}
