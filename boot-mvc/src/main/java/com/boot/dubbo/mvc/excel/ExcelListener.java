package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
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
public class ExcelListener extends AnalysisEventListener<List<String>> {


    private List<List<String>> data = new ArrayList<>();

    @Override
    public void invoke(List<String> object, AnalysisContext context) {
        data.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("context : {}", JSONObject.toJSONString(context));
    }

    public List<List<String>> getData() {
        return data;
    }
}