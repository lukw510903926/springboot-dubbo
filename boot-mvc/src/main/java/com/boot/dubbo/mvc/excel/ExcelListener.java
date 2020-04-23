package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangqi
 * @project : wdzg-adcenter
 * @createTime : 2019-04-01 19:34
 * @email : lukewei@mockuai.com
 * @description : 订单批量发货Excel 解析
 */
@Slf4j
public class ExcelListener extends AnalysisEventListener<Student> {


    private List<Student> list = new ArrayList<>();

    @Override
    public void invoke(Student data, AnalysisContext context) {
        this.list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<Student> getData() {
        return this.list;
    }
}