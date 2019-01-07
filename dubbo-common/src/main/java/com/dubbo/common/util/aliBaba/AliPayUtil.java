package com.dubbo.common.util.aliBaba;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Data
public class AliPayUtil {

    private static Logger logger = LoggerFactory.getLogger(AliPayUtil.class);

    //https://openapi.alipay.com/gateway.do
    private String apiUrl;

    private String appId;

    private String privateKey;

    private String publicKey;

    private String mchId;

    private static AlipayClient alipayClient;


    public static final String DEFAULT_CHARSET = "UTF-8";
    public static String APP_ID;

    public static String MCH_ID;

    public static final Charset ALI_PAY_BILL_CHARSET = Charset.forName("GBK");

    //以下配置写单元测试用的
    //小石头2048
    private static String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1rHVgFTtOwMYgoZnn/La3X51oB45uJ7UhdeXpkVPy935w1pThL9shN+2NKWkmEUbB0hKbvR6hDQBVDoobxCKQBL5Qouj/XoDYppXvLUPPNEm/fS7pQMcawT7g7IJn/SzfkEDfKYt92zxwfVMEabkfrciOvEv9ijvX99EV2vogvA6pHPGTsxlVmF6qttbfTlebaqULBc9o6NgbJ3tM0absRVeZqLVOJ3jJFrSIb6l5Qzd6UeqaclBlV58yydxHV0k8BY4ufSqivgCCNY/1kI5N7BIe0sCf8PoxtEGqzC6EGmQaW6HOmn8bL3TbllQemGoDRoXBfbH/VfzHNKLwMy2pAgMBAAECggEAKJr7QRZ9wwRjRDfaZnedfy7nx7CorQZC/c+UK7hHwy5+HEpZzFsd5zbQ6ZQv5EpIPlntvtFuXyDAykINrFcSQo5M20kOOsWslr/lEyhS16UgNqSML6ZC2kAHBc7W9tcniHB9j2OBwgeWYq4gOARJVENLaFu9wFV9OrAxE2l2Lop4/NeFEvRPZSnAB91o0pBW3+kSg0cjuMo9NkvJYAF9/ZyjBjCu1JpmDkoUfppEdA579iaKFTzoZt16j0IxOS66UucKYRKW8997FoGSZB8jJcMBNVu9LKLZN8ryIpBfhzeqNVOqlJaRA66VsX5kzBmvQSMP0DxVjR4mPW6LOw/gAQKBgQDuA+xrTcG9PjNyq6XIY9mknUh6st+OGdHD4OTUE0sG6tZauTAnA6nlcUk7EUV4I8/aI7uZxcmiacH2TcAwvE37VmrnmSQXXJ+uREk5GrAwA783KKwUrGjzIpUV5ZrHKj5bUlCk63BqW2zl0WUyv+APKbzugWnaFvqTyHPXTr5GgQKBgQDDZq8F4WRixtdWyUhpOOJJ2RVmY3INTJvwAZ48wn5pNsv0BOXNiNODz8LMyGsnKBl2tBRIy5avUKD1tbKOrrCZyZ4Nw63xL6K+WoDyOp9eEHbVyDSb3bhND1QgfSUQQPyWC+PXaaVMoq+FHJNzHicHeJsNDl72XJ4uxRDlv3ZjKQKBgGQUqEOSZYWScwWjoUj6m1h5fjGBpzjUKHzpwq5eEm0tL8bkGZg+FSFSGk3I/GIBBPQflFGbdiZhu9o6ZTbY5x1o//i0TxW3r7kSI4vsaes2mB+0bW8f891QTEnxyrw2ShQSL7C5AwLlwIt+GcRWI/ucPEI5jm3SvbiIUBVXmfCBAoGBAJqicHAe5uqkoVW1BPEAa1Q1z5GvsVqE/gL5xNggsxlgnBcIqTP2zCzMBkU5dlMOkNg3urXBKAalsDvDBwOTi36o58PMz6LHJ+usg88uRWCTdvfH8JpI5MlXECSF5Qbv5vR3hF+842u6c1zMOIQpoIlxEINMlCozMZuhqLWox2/BAoGBAMqlGfqAr26xPOuLDoCi65ToA0g5U5WGtmDnK3XyZRTufLhWgVR57OnL39SIsGpg6s2qj0P/M+sytOtZs7KGy1od6jhMxYhYNIrbdvecSy6c2GaApYhvNmHcY3zWp8DUR8etvWuw2zPbtFF+fByR/sZWez6FcFA4zCn0ocHmVMVT";

    //小石头2048
    private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtax1YBU7TsDGIKGZ5/y2t1+daAeObie1IXXl6ZFT8vd+cNaU4S/bITftjSlpJhFGwdISm70eoQ0AVQ6KG8QikAS+UKLo/16A2KaV7y1DzzRJv30u6UDHGsE+4OyCZ/0s35BA3ymLfds8cH1TBGm5H63IjrxL/Yo71/fRFdr6ILwOqRzxk7MZVZheqrbW305Xm2qlCwXPaOjYGyd7TNGm7EVXmai1Tid4yRa0iG+peUM3elHqmnJQZVefMsncR1dJPAWOLn0qor4AgjWP9ZCOTewSHtLAn/D6MbRBqswuhBpkGluhzpp/Gy9025ZUHphqA0aFwX2x/1X8xzSi8DMtqQIDAQAB";

    //支付宝公钥
    private static String aliPayPublicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArffz9HAyoCHVGXzqB8iMuEM8zfLn/HCIDmMReiYz0rtUZl981NcLz2SxSiAXWA3gqA0v+HqquViFjRXbxlhuP7JW3lu/VAEUxoby1AAEFXypjT7F++XHD4ddVihllxpPlaCkOzwiAdA/iNYPPIG658XZtnb67vDiHuXUqdOuEyQelIB9u5lfAnhxfXcGrOKZwRchnyxXxwcKRjLwJ7wgXgRxJf8m9Q8qCyLqipukAzlz86kAuARahcf3tIxHjO/9HNRHqGiG1zkZfUYyPKF9vEKevUiNPM00FBohz5HJ9sGRYRFr988VfBThVcAxIcLZz7hiOAcVQy6YM8U84ck1mQIDAQAB";

    @PostConstruct
    public void initClient() {
        alipayClient = new DefaultAlipayClient(apiUrl,appId,privateKey,"json",DEFAULT_CHARSET,publicKey,"RSA2");
        APP_ID = appId;
        MCH_ID = mchId;
    }

    /**
     *
     * @param type  可选值trade,signcustomer。trade商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单
     * @param date  yyyy-MM-dd格式
     * @return
     */
    public static  List<String> billDownload(String type,String date){

        if(StringUtils.isBlank(date)){
            return null;
        }
        try{
            AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
            Map<String,String> content = new HashMap<>();
            content.put("bill_type",type);
            content.put("bill_date",date);
            request.setBizContent(JSONObject.toJSONString(content));
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                logger.info("AliPayUtil billDownload invoke success");
                String url = response.getBillDownloadUrl();
                if(StringUtils.isBlank(url)){
                    return null;
                }
                ZipInputStream in = null;
                BufferedReader br = null;
                try {
                    in = new ZipInputStream(new URL(url).openStream(),ALI_PAY_BILL_CHARSET);
                    br = new BufferedReader(new InputStreamReader(in,ALI_PAY_BILL_CHARSET));
                    List<String> resultList = new ArrayList<String>();
                    ZipEntry zipFile;
                    //循环读取zip中的cvs文件，无法使用jdk自带，因为文件名中有中文
                    while ((zipFile=in.getNextEntry())!=null) {
                        if (zipFile.isDirectory()){
                            continue;
                        }
                        //获得cvs名字
                        String fileName = zipFile.getName();
                        logger.info("alipay cvs-----"+fileName);
                        //检测文件是否存在
                        if (fileName != null && fileName.indexOf('.') != -1 && !fileName.contains("汇总")) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                resultList.add(line);
                            }
                        }
                    }
                    return resultList;
                }catch (MalformedURLException ue){
                    logger.error("readAlipayCvsFile url error {}",ue);
                }catch (IOException ioe){
                    logger.error("readAlipayCvsFile io exception {}",ioe);
                }
                finally {
                    try{
                        if(in!=null) {
                            //关闭流
                            in.close();
                        }
                        if(br!=null){
                            br.close();
                        }
                    }catch (Exception e){
                        logger.error("readAlipayCvsFile close stream error",e);
                    }
                }
                return null;
            } else {
                logger.error("AliPayUtil billDownload invoke error");
            }
        }catch (AlipayApiException e){
            logger.error("AliPayUtil billDownload error",e);
        }
        return null;

    }

    /**
     * @param orderId 自己系统的订单id
     * @return
     */
    public static String queryUidWithOrderId(Serializable orderId){

        if(orderId == null){
            return null;
        }
        AlipayTradeQueryResponse response = queryTradeWithOrderId(orderId);
        if(response!= null){
            return response.getBuyerUserId();
        }
        return null;
    }

    /**
     * @param orderId 自己系统的订单id
     * @return
     */
    public static AlipayTradeQueryResponse queryTradeWithOrderId(Serializable orderId){

        if(orderId == null){
            return null;
        }
        try{
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"" + orderId + "\"" +
                    "  }");
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                logger.info("AliPayUtil queryTradeWithOrderId invoke success");
                return response;
            } else {
                logger.error("AliPayUtil queryTradeWithOrderId invoke error");
            }
        }catch (AlipayApiException e){
            logger.error("AliPayUtil queryTradeWithOrderId error",e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        String src = "测试测试";
        String signResult = AlipaySignature.rsa256Sign(src,privateKeyStr,DEFAULT_CHARSET);
        System.out.println(AlipaySignature.rsa256CheckContent(src,signResult,publicKeyStr,DEFAULT_CHARSET));
        AlipayClient testClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2018111262101925",privateKeyStr,"json",DEFAULT_CHARSET,aliPayPublicKeyStr,"RSA2");
        String orderId = "ee0a227881ed456081b247d42c6d7491";
        try{
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\"" + orderId + "\"" +
                    "  }");
            AlipayTradeQueryResponse response = testClient.execute(request);
            if(response.isSuccess()){
                logger.info("AliPayUtil queryTradeWithOrderId invoke success");
                System.out.println(response.getBuyerUserId());
            } else {
                logger.error("AliPayUtil queryTradeWithOrderId invoke error");
            }
        }catch (AlipayApiException e){
            logger.error("AliPayUtil queryTradeWithOrderId error",e);
        }
    }
}
