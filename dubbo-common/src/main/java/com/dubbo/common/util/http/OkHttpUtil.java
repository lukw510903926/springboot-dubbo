package com.dubbo.common.util.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.dubbo.common.util.exception.ServiceException;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody.Builder;

public class OkHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

    private OkHttpUtil() {
    }

    /**
     * get 请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {

        Request.Builder builder = createBuilder(url);
        return execute(builder.get().build());
    }

    /**
     * get 请求
     *
     * @param url
     * @param headers
     * @return
     */
    public static String get(String url, Map<String, String> headers) {

        Request.Builder builder = createBuilder(url);
        headers.forEach(builder::addHeader);
        return execute(builder.get().build());
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params) {

        Request.Builder builder = createBuilder(url);
        Request request = builder.post(formBody(params)).build();
        return execute(request);
    }

    /**
     * post 请求
     *
     * @param url
     * @param params  请求参数
     * @param headers 请求头
     * @return
     */
    public static String post(String url, Map<String, String> params, Map<String, String> headers) {

        Request.Builder builder = createBuilder(url);
        headers.forEach(builder::addHeader);
        return execute(builder.post(formBody(params)).build());
    }

    /**
     * post 请求 发送json数据
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJSON(String url, Map<String, String> params) {

        Request.Builder builder = createBuilder(url);
        RequestBody requestBody = RequestBody.create(mediaType, JSONObject.toJSONString(params));
        return execute(builder.url(url).post(requestBody).build());
    }

    /**
     * post 请求 发送json数据
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String postJSON(String url, Map<String, String> params, Map<String, String> headers) {

        Request.Builder builder = createBuilder(url);
        headers.forEach(builder::addHeader);
        RequestBody requestBody = RequestBody.create(mediaType, JSONObject.toJSONString(params));
        return execute(builder.url(url).post(requestBody).build());
    }

    /**
     * 文件下载
     *
     * @param url
     * @param outputStream
     */
    public static void downLoadFile(String url, OutputStream outputStream) {

        try {
            Request request = createBuilder(url).get().build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                IOUtils.copy(body.byteStream(), outputStream);
            }
        } catch (Exception e) {
            logger.error("文件下载失败 : url : {}, {}", url, e);
            throw new ServiceException("文件下载失败 ");
        }
    }

    private static String execute(Request request) {

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                logger.error("request fail !! {}", response);
                throw new ServiceException("请求失败 :" + response);
            }
            ResponseBody body = response.body();
            return body == null ? null : body.string();
        } catch (IOException e) {
            logger.error("request fail !! {}", e);
            throw new ServiceException("请求失败");
        }
    }

    private static Request.Builder createBuilder(String url) {
        return new Request.Builder().url(url);
    }

    /**
     * 请求body
     *
     * @param params
     * @return
     */
    public static RequestBody formBody(Map<String, String> params) {

        Builder builder = new Builder();
        Map<String, String> treeMap = new TreeMap<>(params);
        Set<Entry<String, String>> set = treeMap.entrySet();
        for (Entry<String, String> entry : set) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                builder.add(key, value);
            }
        }
        return builder.build();
    }

}