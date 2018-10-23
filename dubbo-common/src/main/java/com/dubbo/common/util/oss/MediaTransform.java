package com.dubbo.common.util.oss;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * <p>多媒体转换
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/17 14:21
 **/
public class MediaTransform {

    private static Logger logger = LoggerFactory.getLogger(MediaTransform.class);

    private static IAcsClient client;

    private static void init(TransformProperties properties) {

        String mpsRegionId = properties.getMpsRegionId();
        String accessKeyId = properties.getAccessKeyId();
        String secretAccessKey = properties.getSecretAccessKey();
        DefaultProfile profile = DefaultProfile.getProfile(mpsRegionId, accessKeyId, secretAccessKey);
        client = new DefaultAcsClient(profile);
    }

    public static boolean transform(TransformProperties transformProperties) {

        init(transformProperties);

        SubmitJobsRequest request = new SubmitJobsRequest();

        try {
            // Input
            JSONObject input = new JSONObject();
            input.put("Location", transformProperties.getOssLocation());
            input.put("Bucket", transformProperties.getOssInBucket());
            input.put("Object", URLEncoder.encode(transformProperties.getOssInputObject(), "utf-8"));
            request.setInput(input.toJSONString());

            // Output
            String outputOSSObject = URLEncoder.encode(transformProperties.getOssOutputObject(), "utf-8");

            JSONObject output = new JSONObject();
            output.put("OutputObject", outputOSSObject);
            output.put("TemplateId", transformProperties.getTemplateId());

            JSONArray outputs = new JSONArray();
            outputs.add(output);
            request.setOutputs(outputs.toJSONString());
            request.setOutputBucket(transformProperties.getOssOutBucket());

            request.setOutputLocation(transformProperties.getOssLocation());
            request.setPipelineId(transformProperties.getPipelineId());
            logger.info("response: " + client.getAcsResponse(request).getRequestId());
            return true;
        } catch (Exception e) {
            logger.error("transform 失败 : {}", e);
            return false;
        }
    }

    public static void main(String[] args) {

        TransformProperties properties = new TransformProperties();
        properties.setMpsRegionId("cn-hangzhou");
        properties.setAccessKeyId("accessKeyId").setSecretAccessKey("secretAccessKey");
        properties.setPipelineId("892efc9292574ecd8c118cb28138b65b").setTemplateId("S00000001-100020");
        properties.setOssLocation("oss-cn-hangzhou").setOssInBucket("ywwl-mp3");
        properties.setOssOutBucket("ywwl-m3u8").setOssInputObject("mp3/沧海一声笑.mp3");
        properties.setOssOutputObject("home/canghaiyishengxiao/canghaiyishengxiao");
        transform(properties);
    }
}
