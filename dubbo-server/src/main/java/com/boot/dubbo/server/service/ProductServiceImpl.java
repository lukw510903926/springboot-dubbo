package com.boot.dubbo.server.service;

import com.boot.dubbo.api.api.IProductService;
import com.boot.dubbo.api.entity.Product;
import org.apache.dubbo.config.annotation.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/24 17:17
 **/
@Service
public class ProductServiceImpl implements IProductService {

    @Override
    public List<Product> list(Product product) {
        return Collections.singletonList(product);
    }
}
