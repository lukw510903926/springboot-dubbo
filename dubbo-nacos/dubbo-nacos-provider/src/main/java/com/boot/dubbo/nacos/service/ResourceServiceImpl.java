package com.boot.dubbo.nacos.service;

import com.alibaba.fastjson.JSONObject;
import com.boot.dubbo.api.api.IResourceService;
import com.boot.dubbo.api.entity.Resource;
import com.dubbo.common.util.weChat.WeChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:35
 **/
@Slf4j
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public List<Resource> list() {

        List<Resource> list = new ArrayList<>();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 5; i++) {
            Resource resource = new Resource();
            resource.setId(localRandom.nextInt(100));
            resource.setCreated(LocalDateTime.now().minusDays(localRandom.nextInt(5)));
            resource.setName(localRandom.nextInt(1000000) + "");
            list.add(resource);
        }
        return list;
    }

    @Override
    public String weChatProperties() {
        return JSONObject.toJSONString(weChatProperties);
    }
}
