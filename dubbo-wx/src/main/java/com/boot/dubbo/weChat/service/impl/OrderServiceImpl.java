package com.boot.dubbo.weChat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.weChat.dao.OrderMapper;
import com.boot.dubbo.weChat.entity.Order;
import com.boot.dubbo.weChat.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:40
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
