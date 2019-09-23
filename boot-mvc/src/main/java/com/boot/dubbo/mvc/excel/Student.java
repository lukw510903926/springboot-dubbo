package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email 13507615840@163.com
 * @since 2019/4/1 21:25
 **/
@Data
@Accessors(chain = true)
public class Student implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 5128637163288792163L;

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "生日", index = 1)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM:ss")
    private Date birthday;

    @ExcelIgnore
    private Integer age;
}
