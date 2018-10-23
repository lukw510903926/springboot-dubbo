package com.dubbo.common.util.upload;

import java.io.InputStream;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 14:52
 **/
public interface UploadHelper {

    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param filePath    文件保存路径
     */
    boolean upload(InputStream inputStream, String filePath);

    /**
     * 文件删除
     *
     * @param filePath 文件保存路径
     * @return
     */
    boolean delete(String filePath);
}
