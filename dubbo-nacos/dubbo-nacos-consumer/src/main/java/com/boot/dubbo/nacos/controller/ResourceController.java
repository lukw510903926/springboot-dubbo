package com.boot.dubbo.nacos.controller;

import com.boot.dubbo.api.api.IResourceService;
import com.boot.dubbo.api.entity.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:47
 **/
@Slf4j
@RestController
@RequestMapping("resource")
public class ResourceController {

    @Reference
    private IResourceService resourceService;

    @GetMapping("/list")
    public List<Resource> list() {

        return this.resourceService.list();
    }

    @GetMapping("weChat")
    public String weChat() {
        return this.resourceService.weChatProperties();
    }
}
