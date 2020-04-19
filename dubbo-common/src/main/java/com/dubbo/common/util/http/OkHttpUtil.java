package com.dubbo.common.util.http;

import com.alibaba.fastjson.JSONObject;
import com.dubbo.common.util.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;

import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020/4/19 11:02 下午
 */
@Slf4j
public class OkHttpUtil {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

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
        header(builder, headers);
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
        header(builder, headers);
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
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(params));
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
        header(builder, headers);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, JSONObject.toJSONString(params));
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
            Response response = CLIENT.newCall(request).execute();
            ResponseBody body = response.body();
            if (body != null) {
                IOUtils.copy(body.byteStream(), outputStream);
            }
        } catch (Exception e) {
            log.error("文件下载失败 : url : {}, {}", url, e);
            throw new ServiceException("文件下载失败 ");
        }
    }

    private static String execute(Request request) {

        try {
            Response response = CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("request fail !! {}", response);
                throw new ServiceException("请求失败 :" + response);
            }
            ResponseBody body = response.body();
            return body == null ? null : body.string();
        } catch (Exception e) {
            log.error("request fail !! ", e);
            throw new ServiceException("请求失败");
        }
    }

    private static Request.Builder createBuilder(String url) {
        return new Request.Builder().url(url);
    }


    private static void header(Request.Builder builder, Map<String, String> headers) {
        headers.forEach(builder::addHeader);
    }

    /**
     * 请求body
     *
     * @param params
     * @return
     */
    private static RequestBody formBody(Map<String, String> params) {

        Builder builder = new Builder();
        params.forEach(builder::add);
        return builder.build();
    }
}