package com.dlu.dluBack.mapper;

import com.dlu.dluBack.bean.Cloud_label;
import com.dlu.dluBack.bean.FileAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author hcf
 * @Date 2023/4/25 10:51
 * @Description
 */
public interface FileMapper {
    void addFolder(FileAttr fileAttr);

    List<FileAttr> queryFolderByInviteCode(@Param("invite_code") String invite_code);

    List<FileAttr> queryFolderByFileName(@Param("invite_code") String invite_code, @Param("file_name") String file_name);

    void fileRename(@Param("path") String path,@Param("reName") String reName,@Param("newPath") String newPath);

    void deleteFileByPath(@Param("path") String path);

    void addLabels(Cloud_label cloud_label);

    List<Cloud_label> queryCloudLabels(@Param("name") String name);

    void delete_cloud_labels(@Param("labels_name") String labels_name);
}
