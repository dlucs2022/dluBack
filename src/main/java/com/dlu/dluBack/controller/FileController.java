package com.dlu.dluBack.controller;



import com.dlu.dluBack.bean.Cloud_label;
import com.dlu.dluBack.bean.FileAttr;
import com.dlu.dluBack.bean.ImageAttr;
import com.dlu.dluBack.bean.MultipartFileParam;
import com.dlu.dluBack.service.FileService;
import com.dlu.dluBack.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 * @Author hcf
 * @Date 2023/4/23 20:55
 * @Description
 */

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Value("${ftp.server_file_path}")
    public String server_file_path;

    @Value("${ftp.tmp_file}")
    public String tmp_file;


//    String SINGLE_FOLDER = server_file_path;
    @ApiOperation("大文件分片上传")
    @ResponseBody
    @PostMapping("/uploadCategory")
    public void uploadCategory(MultipartFileParam param,
                               HttpServletRequest request,
                               HttpServletResponse response
                               )throws Exception {

        String SINGLE_FOLDER = server_file_path;

        File file = new File(SINGLE_FOLDER);

        String path=file.getAbsolutePath();

        String s = param.getRelativePath().split("/")[0];
        path += "/"+param.getUserName()+"/"+s;

        File fileDir = new File(path);

        if(!fileDir.exists()){

            fileDir.mkdirs();
            //如果初次创建，则在db中insert一条记录

        }else {
            //如果不是初次创建，则update记录
        }

        response.setContentType("text/html;charset=UTF-8");
        try {
            /**
             * 判断前端Form表单格式是否支持文件上传
             */
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if(!isMultipart){
                //这里是我向前端发送数据的代码，可理解为 return 数据; 具体的就不贴了
                System.out.println("不支持的表单格式");
                response.setStatus(404);
                response.getOutputStream().write("不支持的表单格式".getBytes());
            }else {
                param.setTaskId(param.getIdentifier());
                fileService.chunkUploadByMappedByteBuffer(param,path);//service层
                response.setStatus(200);
                response.getWriter().print("上传成功");
            }
            response.getWriter().flush();

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传文件失败");
            response.setStatus(415);
        }
    }


    @ResponseBody
    @PostMapping("folderComplete")
    public ResultVo folderComplete(FileAttr fileAttr){
        fileAttr.setPath(server_file_path+"/"+fileAttr.getUserName()+"/"+fileAttr.getFile_name());
        fileAttr.setIs_use(0);
        fileAttr.setProgress(0);
        fileAttr.setType(0);
        fileService.addFolder(fileAttr);
        return ResultVo.success("success");
    }

    @ResponseBody
    @PostMapping("queryFolderByInviteCode")
    public ResultVo queryFolderByInviteCode(String invite_code){
        List<FileAttr> list = fileService.queryFolderByInviteCode(invite_code);
        return ResultVo.success(list);
    }

    @ResponseBody
    @PostMapping("queryFolderByFileName")
    public ResultVo queryFolderByFileName(String invite_code,String file_name){
        List<FileAttr> list = fileService.queryFolderByFileName(invite_code,file_name);
        return ResultVo.success(list);
    }

    @ResponseBody
    @PostMapping("fileRename")
    public ResultVo fileRename(String path, String reName){
        fileService.fileRename(path,reName);
        return ResultVo.success("success");
    }

    @ResponseBody
    @PostMapping("deleteFileByPath")
    public ResultVo deleteFileByPath(String path){
        fileService.deleteFileByPath(path);
        return ResultVo.success("success");
    }

    @ResponseBody
    @PostMapping("queryImgList")
    public ResultVo queryImgList(String path){
        List<String> imgList = fileService.queryImgList(path);
        return ResultVo.success(imgList);
    }

    @ResponseBody
    @PostMapping("upload_now_lebels")
    public ResultVo upload_now_lebels(String name,String labels_name,@RequestBody Map hasMap){
        Cloud_label cloud_label = new Cloud_label();
        cloud_label.setName(name);
        cloud_label.setLabels_name(name+"_-"+labels_name);
        cloud_label.setLabels_data((String) hasMap.get("labels"));
        fileService.addLabels(cloud_label);
        return ResultVo.success("success");
    }

    @ResponseBody
    @PostMapping("queryCloudLabels")
    public ResultVo queryCloudLabels(String name){
        List<Cloud_label> cloud_labels =  fileService.queryCloudLabels(name);
        return ResultVo.success(cloud_labels);
    }

    @ResponseBody
    @PostMapping("delete_cloud_labels")
    public ResultVo delete_cloud_labels(String labels_name){
        fileService.delete_cloud_labels(labels_name);
        return ResultVo.success("success");
    }

    @ResponseBody
    @RequestMapping("species_recognition")
    public ResultVo species_recognition(String userName,MultipartFile file,HttpServletRequest request){
        String path = "";
        if ("".equals(userName)){       //用户未登录
            path = fileService.species_recognition_Without_user(file,tmp_file);
        }else{
            path = fileService.species_recognition(userName,file,server_file_path);
        }
//
        return ResultVo.success(path);
    }
}
