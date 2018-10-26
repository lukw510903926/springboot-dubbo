package com.boot.dubbo.mvc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.dubbo.api.entity.Product;
import com.boot.dubbo.api.mapper.IProductMapper;
import com.boot.dubbo.mvc.service.IProductService;
import com.dubbo.common.util.RequiredParameterValidator;
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

        return StringUtils.isBlank(product.getDescription());
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(Product product) {

        RequiredParameterValidator.Result result = RequiredParameterValidator.validate(product, "description");
        if (!result.isSuccess()) {
            throw new RuntimeException(result.getMsg());
        }
        return true;
    }
}
