package com.dubbo.common.util.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * <p> oss 上传工具类
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 14:37
 **/
public class OssUtil {

    private static final Logger logger = LoggerFactory.getLogger(OssUtil.class);

    /**
     * 新建Bucket --Bucket权限:私有
     *
     * @param bucketName bucket名称
     * @return true 新建Bucket成功
     */
    public static final boolean createBucket(OSS client, String bucketName) {
        Bucket bucket = client.createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * 删除Bucket
     *
     * @param bucketName bucket名称
     */
    public static final void deleteBucket(OSS client, String bucketName) {

        client.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client     OSS客户端
     * @param file       上传文件
     * @param bucketName bucket名称
     * @param filePath   上传文件的目录 home/201709/20/aaa.xml
     */
    public static final String uploadObject2OSS(OSS client, File file, String bucketName, String filePath) {

        String result = null;
        if (file != null) {
            filePath = getFilePath(filePath);
            ObjectMetadata metadata = new ObjectMetadata();
            //metadata.setContentLength(inputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(filePath));
            PutObjectResult putResult = client.putObject(bucketName, filePath, file, metadata);
            result = putResult.getETag();
        }
        return result;
    }

    /**
     * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
     *
     * @param client      OSS客户端
     * @param inputStream 文件流
     * @param bucketName  bucket名称
     * @param filePath    上传文件的目录 home/201709/20/aaa.xml
     */
    public static final String uploadObject2OSS(OSS client, InputStream inputStream, String bucketName, String filePath) {

        String result = null;
        if (inputStream != null) {
            filePath = getFilePath(filePath);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(filePath));
            PutObjectResult putResult = client.putObject(bucketName, filePath, inputStream, metadata);
            result = putResult.getETag();
        }
        return result;
    }

    /**
     * 根据key获取OSS服务器上的文件输入流
     *
     * @param client     OSS客户端
     * @param bucketName bucket名称
     * @param filePath   Bucket下的文件的路径名+文件名  home/201709/20/aaa.xml
     */
    public static final InputStream getOSS2InputStream(OSS client, String bucketName, String filePath) {
        filePath = getFilePath(filePath);
        OSSObject ossObj = client.getObject(bucketName, filePath);
        return ossObj.getObjectContent();
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param client     OSS客户端
     * @param bucketName bucket名称
     * @param filePath   Bucket下的文件的路径名+文件名  home/201709/20/aaa.xml
     */
    public static void deleteFile(OSS client, String bucketName, String filePath) {

        filePath = getFilePath(filePath);
        client.deleteObject(bucketName, filePath);
        logger.info("删除" + bucketName + "下的文件" + filePath + "成功");
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("bmp".equalsIgnoreCase(fileExtension))
            return "image/bmp";
        if ("gif".equalsIgnoreCase(fileExtension))
            return "image/gif";
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension) || "png".equalsIgnoreCase(fileExtension))
            return "image/png";
        if ("html".equalsIgnoreCase(fileExtension))
            return "text/html";
        if ("txt".equalsIgnoreCase(fileExtension))
            return "text/plain";
        if ("vsd".equalsIgnoreCase(fileExtension))
            return "application/vnd.visio";
        if ("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension))
            return "application/vnd.ms-powerpoint";
        if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension) || "pdf".equalsIgnoreCase(fileExtension))
            return "application/msword";
        if ("xml".equalsIgnoreCase(fileExtension))
            return "text/xml";
        return "application/msword";
    }

    /**
     * 将/home/aa.xml 转为home/aa.xml
     *
     * @param filePath
     * @return
     */
    private static String getFilePath(String filePath) {

        if (StringUtils.isNotBlank(filePath) && filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        filePath.replace("//", "/");
        return filePath;
    }
}
