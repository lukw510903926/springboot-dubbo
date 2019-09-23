package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.dubbo.common.util.oss.OssUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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

    private static OSS ossClient;

    static {
        OSSBuilder builder = new OSSClientBuilder();
        ossClient = builder.build("oss-cn-hangzhou.aliyuncs.com", "LTAIKbzVDNNREHJj", "ZxQ5cpsdGbefqRJZuiokSabHRI5Db1");
    }

    public static void main(String[] args) throws Exception {

        uploadFile();
    }


    static void downloadFile() {
        InputStream oss2InputStream = OssUtil.getOSS2InputStream(ossClient, "ywwl-m3u8", "file/设备成本模板.xlsx");
        log.info("oss2InputStream : {}", oss2InputStream);
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
