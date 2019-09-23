package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
        //uploadFile();
        readExcel();
    }


    static void readExcel() throws Exception {

        ReadWorkbook readWorkbook = new ReadWorkbook();
        ExcelListener excelListener = new ExcelListener();
        readWorkbook.setClazz(Student.class);
        readWorkbook.setCustomReadListenerList(Lists.newArrayList(excelListener));
        readWorkbook.setInputStream(FileUtils.openInputStream(new File("/Users/yangqi/Desktop/2019-08-10_data.xlsx")));
        ExcelReader excelReader = new ExcelReader(readWorkbook);
        excelReader.read();
        List<Student> data = excelListener.getData();
        System.out.println(data);
    }

    private static void uploadFile() throws Exception {

        List<Student> list = new ArrayList<>();
        StyleExcelHandler handler = new StyleExcelHandler();
        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            student.setAge(i);
            student.setName("name" + ThreadLocalRandom.current().nextInt(1000)).setBirthday(new Date());
            list.add(student);
        }
        File file = new File("/Users/yangqi/Desktop/2019-08-10_data.xlsx");
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        FileOutputStream outputStream = new FileOutputStream(file);
        writeWorkbook.setOutputStream(outputStream);
        writeWorkbook.setClazz(Student.class);
        writeWorkbook.setExcelType(ExcelTypeEnum.XLSX);
        writeWorkbook.setCustomWriteHandlerList(Lists.newArrayList(handler));
        ExcelWriter writer = new ExcelWriter(writeWorkbook);
        WriteSheet writeSheet = new WriteSheet();
        //sheet1.setCustomWriteHandlerList(Lists.newArrayList(handler));
        writer.write(list, writeSheet);
        writer.finish();
    }

}
