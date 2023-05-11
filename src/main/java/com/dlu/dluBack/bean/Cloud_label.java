package com.dlu.dluBack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hcf
 * @Date 2023/5/4 17:38
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cloud_label {
    private String labels_name;
    private String name;
    private String labels_data;
}
