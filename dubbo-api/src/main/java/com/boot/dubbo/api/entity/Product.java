package com.boot.dubbo.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email yangqi@ywwl.com
 * @since 2018/10/23 18:26
 **/
@TableName("t_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 5421485971865672095L;

    /**
     * 主键ID , 这里故意演示注解可以无
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long price;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
