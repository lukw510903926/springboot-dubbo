package com.dubbo.common.util.oss;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 14:23
 **/
public class TransformProperties {

    /**
     * Location
     */
    private String mpsRegionId;

    private String accessKeyId;

    private String secretAccessKey;

    /**
     * 管道
     */
    private String pipelineId;

    /**
     * 转码模版
     */
    private String templateId;

    private String ossLocation;

    /**
     * 源文件bucket
     */
    private String ossInBucket;

    /**
     * 目标文件bucket
     */
    private String ossOutBucket;

    /**
     * 源文件 home/myAll.mp3
     */
    private String ossInputObject;

    /**
     * home/myAll
     * 转码后的文件 不需要后缀名
     */
    private String ossOutputObject;

    public String getMpsRegionId() {
        return mpsRegionId;
    }

    public TransformProperties setMpsRegionId(String mpsRegionId) {
        this.mpsRegionId = mpsRegionId;
        return  this;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public TransformProperties setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return  this;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public TransformProperties setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TransformProperties setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return  this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public TransformProperties setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public String getOssLocation() {
        return ossLocation;
    }

    public TransformProperties setOssLocation(String ossLocation) {
        this.ossLocation = ossLocation;
        return this;
    }

    public String getOssInBucket() {
        return ossInBucket;
    }

    public TransformProperties setOssInBucket(String ossInBucket) {
        this.ossInBucket = ossInBucket;
        return this;
    }

    public String getOssOutBucket() {
        return ossOutBucket;
    }

    public TransformProperties setOssOutBucket(String ossOutBucket) {
        this.ossOutBucket = ossOutBucket;
        return this;
    }

    public String getOssInputObject() {
        return ossInputObject;
    }

    public TransformProperties setOssInputObject(String ossInputObject) {
        this.ossInputObject = ossInputObject;
        return this;
    }

    public String getOssOutputObject() {
        return ossOutputObject;
    }

    public TransformProperties setOssOutputObject(String ossOutputObject) {
        this.ossOutputObject = ossOutputObject;
        return this;
    }
}
