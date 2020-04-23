package com.dubbo.common.util.alibaba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.aliyun.log.producer.LogProducer;
import com.aliyun.openservices.aliyun.log.producer.Producer;
import com.aliyun.openservices.aliyun.log.producer.ProducerConfig;
import com.aliyun.openservices.aliyun.log.producer.ProjectConfig;
import com.aliyun.openservices.aliyun.log.producer.ProjectConfigs;
import com.aliyun.openservices.log.common.LogItem;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-12-13 19:15
 * @email : lukewei@mockuai.com
 * @description :
 */
public class LogHubHelper implements InitializingBean {

    @Autowired
    private LogHubProperties logHubProperties;

    private Producer producer;

    @Override
    public void afterPropertiesSet() {

        ProjectConfigs projectConfigs = new ProjectConfigs();
        String project = this.logHubProperties.getProject();
        String endpoint = this.logHubProperties.getEndpoint();
        String accessKeyId = this.logHubProperties.getAccessKeyId();
        String accessKeySecret = this.logHubProperties.getAccessKeySecret();
        ProjectConfig projectConfig = new ProjectConfig(project, endpoint, accessKeyId, accessKeySecret);
        projectConfigs.put(projectConfig);
        ProducerConfig producerConfig = new ProducerConfig(projectConfigs);
        producerConfig.setBatchSizeThresholdInBytes(3 * 1024 * 1024);
        producerConfig.setBatchCountThreshold(40960);
        this.producer = new LogProducer(producerConfig);
    }

    public void send(final Object object, String store) throws Exception {


        LogItem logItem = getLogItem(object);
        this.producer.send(this.logHubProperties.getProject(), store, logItem);
    }

    /**
     * @param object
     * @return
     */
    private static LogItem getLogItem(Object object) {

        LogItem logItem = new LogItem();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        jsonObject.forEach((key, value) -> logItem.PushBack(key, value + ""));
        logItem.PushBack("_push_time", LocalDateTime.now().toString());
        return logItem;
    }

}
