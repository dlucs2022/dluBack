package com.dlu.dluBack.service;

import com.dlu.dluBack.bean.Cloud_label;
import com.dlu.dluBack.bean.FileAttr;
import com.dlu.dluBack.bean.MultipartFileParam;

import java.util.List;

/**
 * @Author hcf
 * @Date 2023/4/24 11:36
 * @Description
 */
public interface FileService {

    String chunkUploadByMappedByteBuffer(MultipartFileParam param, String path)throws Exception;

    void addFolder(FileAttr fileAttr);

    List<FileAttr> queryFolderByInviteCode(String invite_code);

    List<FileAttr> queryFolderByFileName(String invite_code, String file_name);

    void fileRename(String path, String reName);

    void deleteFileByPath(String path);

    List<String> queryImgList(String path);

    void addLabels(Cloud_label cloud_label);

    List<Cloud_label> queryCloudLabels(String name);

    void delete_cloud_labels(String labels_name);
}
