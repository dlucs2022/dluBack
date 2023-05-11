package com.dlu.dluBack.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author hcf
 * @Date 2023/4/24 16:00
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileAttr {
    //名字
    private String userName;
    //邀请码
    private String invite_code;
    //上传时间
    private String create_time;
    //最后一个修改时间
    private String last_update_time;
    //文件大小
    private int size;
    //子文件个数
    private int file_number;
    //文件夹名字
    private String file_name;
    //标记进度
    private int progress;
    //当前是否有人进行标注  1有人   0无人
    private int is_use;
    //文件路径
    private String path;
    //文件类型  文件夹：0  文件：1
    private int type;
}
