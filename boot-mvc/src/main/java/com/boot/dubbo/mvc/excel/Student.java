package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email 13507615840@163.com
 * @since 2019/4/1 21:25
 *
 **/
@Data
@Accessors(chain = true)
public class Student extends BaseRowModel implements Serializable {
    private static final long serialVersionUID = -1828934299449325280L;

    @ExcelProperty(value = "姓名",index = 1)
    private String name;

    @ExcelProperty(value =  "生日",index = 2,format = "yyyy-MM-dd HH:MM:ss")
    private Date birthday;
}
