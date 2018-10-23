package com.dubbo.common.util.upload;

import com.aliyun.oss.OSS;
import com.dubbo.common.util.oss.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 14:56
 **/
public class OssUploadHelper implements UploadHelper {

    private OSS oss;

    private String bucketName;

    @Autowired
    public void setOss(OSS oss) {
        this.oss = oss;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public boolean upload(InputStream inputStream, String filePath) {

        OssUtil.uploadObject2OSS(oss, inputStream, bucketName, filePath);
        return true;
    }

    @Override
    public boolean delete(String filePath) {
        OssUtil.deleteFile(oss, bucketName, filePath);
        return true;
    }
}
