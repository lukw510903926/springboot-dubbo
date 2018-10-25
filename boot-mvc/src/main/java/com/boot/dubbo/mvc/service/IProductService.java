package com.boot.dubbo.mvc.service;


import com.boot.dubbo.api.entity.Product;
import com.dubbo.common.service.IBaseService;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/23 18:30
 **/
public interface IProductService extends IBaseService<Product> {

    boolean saveProduct(Product product);
}
