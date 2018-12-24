package com.boot.dubbo.client.controller;

import com.boot.dubbo.api.api.IProductService;
import com.boot.dubbo.api.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2018/12/24 17:21
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public List<Product> list(){

        Product product = new Product();
        product.setDescription("产品描述");
        product.setPrice(230000L);
        return this.productService.list(product);
    }
}
