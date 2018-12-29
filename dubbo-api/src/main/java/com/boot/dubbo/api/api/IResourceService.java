package com.boot.dubbo.api.api;

import com.boot.dubbo.api.entity.Resource;

import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/29 17:38
 **/
public interface IResourceService {

    List<Resource> list();
}
