package com.boot.dubbo.weChat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.weChat.dao.ProductMapper;
import com.boot.dubbo.weChat.entity.Product;
import com.boot.dubbo.weChat.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/11/28 11:41
 **/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
