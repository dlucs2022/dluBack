package com.dlu.dluBack.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author hcf
 * @Date 2023/4/2 9:43
 * @Description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {
    private int pageNum;
    private int pageSize;
    private long total;
    private int pageCount;
    private List<T> rows;
}
