package com.dlu.dluBack.service.impl;

import com.dlu.dluBack.bean.Cloud_label;
import com.dlu.dluBack.bean.FileAttr;
import com.dlu.dluBack.bean.MultipartFileParam;
import com.dlu.dluBack.mapper.FileMapper;
import com.dlu.dluBack.service.FileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author hcf
 * @Date 2023/4/24 11:41
 * @Description
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public String chunkUploadByMappedByteBuffer(MultipartFileParam param, String filePath) throws Exception{

        if(param.getTaskId() == null || "".equals(param.getTaskId())){
            param.setTaskId(UUID.randomUUID().toString());
        }
        /**
         *
         * 1：创建临时文件，和源文件一个路径
         * 2：如果文件路径不存在重新创建
         */
        String fileName = param.getFile().getOriginalFilename();
        String tempFileName = param.getTaskId() + fileName.substring(fileName.lastIndexOf(".")) + "_tmp";
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        File tempFile = new File(filePath,tempFileName);
        //第一步
        RandomAccessFile raf = new RandomAccessFile(tempFile,"rw");
        //第二步
        FileChannel fileChannel = raf.getChannel();
        //第三步 计算偏移量
        long position = (param.getChunkNumber()-1) * param.getChunkSize();
        //第四步
        byte[] fileData = param.getFile().getBytes();
        //第五步
        long end=position+fileData.length-1;
        fileChannel.position(position);
        fileChannel.write(ByteBuffer.wrap(fileData));
        //使用 fileChannel.map的方式速度更快，但是容易产生IO操作，无建议使用
//        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,position,fileData.length);
//        //第六步
//        mappedByteBuffer.put(fileData);        //第七步
//        freedMappedByteBuffer(mappedByteBuffer);
//        Method method = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
//        method.setAccessible(true);
//        method.invoke(FileChannelImpl.class, mappedByteBuffer);
        fileChannel.force(true);
        fileChannel.close();
        raf.close();
        //第八步
        boolean isComplete = checkUploadStatus(param,fileName,filePath);
        if(isComplete){
            //重命名文件，然后校验MD5文件是否一致
            FileInputStream fileInputStream = new FileInputStream(tempFile.getPath());
            String md5= DigestUtils.md5Hex(fileInputStream);
            fileInputStream.close();
            renameFile(tempFile,fileName);
            if(StringUtils.isNotBlank(md5) && !md5.equals(param.getIdentifier())){
                //不是同一文件抛出异常
                System.out.println("不是同一文件抛出异常");
            }
        }
        return param.getTaskId();
    }

    @Override
    public void addFolder(FileAttr fileAttr) {
        fileMapper.addFolder(fileAttr);
    }

    @Override
    public List<FileAttr> queryFolderByInviteCode(String invite_code) {
        return fileMapper.queryFolderByInviteCode(invite_code);
    }

    @Override
    public List<FileAttr> queryFolderByFileName(String invite_code, String file_name) {
        return fileMapper.queryFolderByFileName(invite_code,file_name);
    }

    @Override
    public void fileRename(String path, String reName) {
        File folder = new File(path); // create a File object with the folder path
        if (folder.exists() && folder.isDirectory()) { // check if the folder exists and is a directory
            File newFolder = new File(folder.getParent(), reName); // create a new File object with the parent directory and the new folder name
            if (folder.renameTo(newFolder)) { // rename the folder using the new File object
                System.out.println("Folder renamed successfully."); // print success message
            } else {
                System.out.println("Failed to rename folder."); // print error message
            }
        } else {
            System.out.println("Folder does not exist or is not a directory."); // print error message
        }
        File file = new File(folder.getParent(),reName);
        if (file.exists()){
            String newPath = file.getParent()+"/"+reName;
            fileMapper.fileRename(path,reName,newPath);
        }


    }

    @Override
    public void deleteFileByPath(String path) {
        deleteFolder(path);
        File file = new File(path);
        if (!file.exists()){
            fileMapper.deleteFileByPath(path);
        }else {
            System.out.println("删除文件夹出错！");
        }

    }

    @Override
    public List<String> queryImgList(String path) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                fileNames.add(file.getName());
            }
        }
        return fileNames;
    }

    @Override
    public void addLabels(Cloud_label cloud_label) {
        fileMapper.addLabels(cloud_label);
    }

    @Override
    public List<Cloud_label> queryCloudLabels(String name) {
        return fileMapper.queryCloudLabels(name);
    }

    @Override
    public void delete_cloud_labels(String labels_name) {
        fileMapper.delete_cloud_labels(labels_name);
    }

    // 工具方法：递归删除一个文件夹
    public void deleteFolder(String folderPath) {
        File folder = new File(folderPath); // create a File object with the folder path
        if (folder.exists() && folder.isDirectory()) { // check if the folder exists and is a directory
            File[] files = folder.listFiles(); // get a list of all files and directories in the folder
            for (File file : files) { // loop through each file/directory
                if (file.isDirectory()) { // if it's a directory, recursively call this method to delete it and its contents
                    deleteFolder(file.getAbsolutePath());
                } else { // if it's a file, delete it
                    file.delete();
                }
            }
            folder.delete(); // delete the empty folder
            System.out.println("Folder deleted successfully."); // print success message
        } else {
            System.out.println("Folder does not exist or is not a directory."); // print error message
        }
    }

    /**
     * 文件重命名
     * @param toBeRenamed   将要修改名字的文件
     * @param toFileNewName 新的名字
     * @return
     */
    public void renameFile(File toBeRenamed, String toFileNewName) throws IOException {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            System.out.println("文件不存在");
            return;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        FileUtils.moveFile(toBeRenamed,newFile);
//        System.out.println(toBeRenamed.renameTo(newFile));
    }

    /**
     * 检查文件上传进度
     * @return
     */
    public boolean checkUploadStatus(MultipartFileParam param,String fileName,String filePath) throws IOException {
        File confFile = new File(filePath,fileName+".conf");
        RandomAccessFile confAccessFile = new RandomAccessFile(confFile,"rw");
        //设置文件长度
        confAccessFile.setLength(param.getTotalChunks());
        //设置起始偏移量
        confAccessFile.seek(param.getChunkNumber()-1);
        //将指定的一个字节写入文件中 127，
        confAccessFile.write(Byte.MAX_VALUE);
        byte[] completeStatusList = FileUtils.readFileToByteArray(confFile);
        confAccessFile.close();//不关闭会造成无法占用
        //这一段逻辑有点复杂，看的时候思考了好久，创建conf文件文件长度为总分片数，每上传一个分块即向conf文件中写入一个127，那么没上传的位置就是默认的0,已上传的就是Byte.MAX_VALUE 127
        for(int i = 0; i<completeStatusList.length; i++){
            if(completeStatusList[i]!=Byte.MAX_VALUE){
                return false;
            }
        }
        //如果全部文件上传完成，删除conf文件
        confFile.delete();
        return true;
    }

    /**
     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检 查是否还有线程在读或写
     * @param mappedByteBuffer
     */
    public static void freedMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }
            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        //可以访问private的权限
                        getCleanerMethod.setAccessible(true);
                        //在具有指定参数的 方法对象上调用此 方法对象表示的底层方法
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
                                new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("清理缓存出错!!!"+e.getMessage());
                    }
                    System.out.println("缓存清理完毕!!!");
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
