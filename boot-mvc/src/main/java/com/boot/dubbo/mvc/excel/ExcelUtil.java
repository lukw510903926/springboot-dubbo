package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSBuilder;
import com.aliyun.oss.OSSClientBuilder;
import com.dubbo.common.util.oss.OssUtil;

import java.io.*;
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
public class ExcelUtil {

    private static OSS ossClient;

    static {
        OSSBuilder builder = new OSSClientBuilder();
        ossClient = builder.build("oss-cn-hangzhou.aliyuncs.com", "LTAIKbzVDNNREHJj", "ZxQ5cpsdGbefqRJZuiokSabHRI5Db1");
    }

    public static void main(String[] args) throws Exception {

//        downloadFile();
        uploadFile();
    }


    static void downloadFile() {
        InputStream oss2InputStream = OssUtil.getOSS2InputStream(ossClient, "ywwl-m3u8", "file/设备成本模板.xlsx");
        System.out.println(oss2InputStream);
    }

    private static void uploadFile() throws Exception {

        List<Student> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            student.setName("name" + ThreadLocalRandom.current().nextInt(1000)).setBirthday(new Date());
            list.add(student);
        }
        File file = File.createTempFile("789", "xlsx");
        try (
                FileOutputStream out = new FileOutputStream(file)) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0, Student.class);
            writer.write(list, sheet1);
            writer.finish();
            FileInputStream fileInputStream = new FileInputStream(file);
            OssUtil.uploadObject2OSS(ossClient, fileInputStream, "ywwl-m3u8", "file/789.xlsx");

//            https://ywwl-m3u8.oss-cn-hangzhou.aliyuncs.com/file/789.xlsx
//            https://bucketname.endpint/key
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
