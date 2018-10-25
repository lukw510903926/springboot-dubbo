package com.boot.dubbo.mvc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.entity.Product;
import com.boot.dubbo.api.mapper.IProductMapper;
import com.boot.dubbo.mvc.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/23 18:30
 **/
@Service
public class ProductServiceImpl extends ServiceImpl<IProductMapper, Product> implements IProductService {

    @Override
    @Transactional
    public boolean saveProduct(Product product) {

        if (StringUtils.isBlank(product.getDescription())) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(Product product) {

        if (StringUtils.isBlank(product.getDescription())) {
            throw  new RuntimeException("产品描述不可为空");
        }
        return true;
    }
}
