package com.dlu.dluBack.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Author hcf
 * @Date 2023/4/23 22:12
 * @Description
 */
@ApiModel("大文件分片入参实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipartFileParam {
    @ApiModelProperty("文件传输任务ID")
    private String taskId;

    @ApiModelProperty("当前为第几分片")
    private int chunkNumber;

    @ApiModelProperty("每个分块的大小")
    private long chunkSize;


    @ApiModelProperty("分片总数")
    private int totalChunks;
    @ApiModelProperty("文件唯一标识")
    private String identifier;


    @ApiModelProperty("分块文件传输对象")
    private MultipartFile file;

    /**
     * 相对路径
     */
    private String relativePath;
    // 上传者
    private String userName;
    // 邀请码
    private String invite_code;
    // 文件大小
    private String file_size;
    // 父文件夹




}
