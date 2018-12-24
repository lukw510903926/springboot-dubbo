package com.boot.dubbo.api.api;

import com.boot.dubbo.api.entity.Product;

import java.util.List;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/12/24 17:16
 **/
public interface IProductService {

    List<Product> list(Product product);
}
