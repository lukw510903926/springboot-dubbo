package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

/**
 * <p>
 *
 * @author yangqi
 * @Description </p>
 * @email 13507615840@163.com
 * @since 2019/4/1 21:29
 **/
@Slf4j
public class ExcelUtil {

    public static void main(String[] args) throws Exception {
        readMap();
//        readExcel();
    }


    static void readMap() throws Exception {
        String fileName = "/Users/yangqi/Downloads/执行结果1.xlsx";
        FileInputStream inputStream = FileUtils.openInputStream(new File(fileName));
        MapListener excelListener = new MapListener();
        ExcelReaderBuilder readerBuilder = EasyExcel.read(inputStream, excelListener);
        readerBuilder.sheet().doRead();
        List<Map<Integer, String>> list = excelListener.getList();
        System.out.println(JSON.toJSONString(list));
        list.forEach(entity -> {
            StringBuilder builder = new StringBuilder("update integral_account set used_integral = used_integral - ");
            builder.append(entity.get(2)).append(" where id = ").append(entity.get(0)).append(" ; ");
            System.out.println(builder);
        });
    }

    static void readExcel() throws Exception {
        String fileName = "/Users/yangqi/Desktop/2019-08-10_data.xlsx";
        FileInputStream inputStream = FileUtils.openInputStream(new File(fileName));
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(inputStream, Student.class, excelListener).sheet().doRead();
        List<Student> data = excelListener.getData();
        System.out.println(JSON.toJSONString(data));
    }

    private static void uploadFile() throws Exception {

        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setName("name" + ThreadLocalRandom.current().nextInt(1000));
            student.setBirthday(new Date());
            list.add(student);
        }
        File file = new File("/Users/yangqi/Desktop/2019-08-10_data.xlsx");
        FileOutputStream outputStream = new FileOutputStream(file);
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        writeSheet.setCustomWriteHandlerList(Lists.newArrayList(new StyleExcelHandler()));
        ExcelWriter writer = EasyExcel.write(outputStream, Student.class).build();
        writer.write(list, writeSheet);
        writer.finish();
    }
}
