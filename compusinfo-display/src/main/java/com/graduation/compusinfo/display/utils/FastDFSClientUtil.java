package com.graduation.compusinfo.display.utils;

import com.alibaba.druid.util.StringUtils;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.*;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author zzk
 * @date 2020/3/14 2:20
 */
@Component
public class FastDFSClientUtil {
    private final Logger logger = LoggerFactory.getLogger(FastDFSClientUtil.class);
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     *  file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFileStream(InputStream is, long size, String fileName) throws IOException {
        StorePath storePath = storageClient.uploadFile(is,size, FilenameUtils.getExtension(fileName),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 上传文件
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream (file);
        StorePath storePath = storageClient.uploadFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()),null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     * @param content 文件内容
     * @param fileExtension
     * @return
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream,buff.length, fileExtension,null);
        return getResAccessUrl(storePath);
    }

    // 封装图片完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = fdfsWebServer.getWebServerUrl() + storePath.getFullPath();
        return fileUrl;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn(e.getMessage());
        }
    }


    /**
     *  图片缩略图
     * @param is
     * @param size
     * @param fileExtName
     * @param metaData
     * @return 文件访问地址
    返回地址为：http://192.168.6.24:8977/group1/M00/00/00/wKgGGFnDGS-AVhrAAAMhasozgRc678.jpg
    访问缩略图地址为：http://192.168.6.24:8977/group1/M00/00/00/wKgGGFnDGS-AVhrAAAMhasozgRc678_150x150.jpg
     */
//    public String upfileImage(InputStream is,long size,String fileExtName,Set<MateData> metaData){
//        StorePath path = storageClient.uploadImageAndCrtThumbImage(is,size,fileExtName,metaData);
//
//        return getResAccessUrl(path);
//    }


}
