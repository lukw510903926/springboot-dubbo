package com.dubbo.common.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Description: rest http 请求
 * @author: yangqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月10日 下午7:05:47
 */
public class RestHttpClient {

    private static final RestTemplate RESTTEMPLATE = new RestTemplate();

    private RestHttpClient() {
    }

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        RESTTEMPLATE.setRequestFactory(requestFactory);
    }

    public static String get(String url) {

        return get(url, String.class);
    }

    /**
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Class<V> resultType) {

        return RESTTEMPLATE.getForObject(url, resultType);
    }

    /**
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param header     请求header
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Class<V> resultType, Map<String, String> header) {

        HttpEntity<String> requestEntity = new HttpEntity<>(header(header, MediaType.APPLICATION_JSON_UTF8));
        return RESTTEMPLATE.exchange(url, HttpMethod.GET, requestEntity, resultType).getBody();
    }

    public static String post(String url, Object param) {

        return post(url, param, String.class, new HashMap<>());
    }

    /**
     * json 请求
     *
     * @param url        请求参数
     * @param resultType 返回结果类型
     * @param param      请求参数
     * @param <V>
     * @return
     */
    public static <V> V post(String url, Object param, Class<V> resultType) {

        return post(url, param, resultType, new HashMap<>());
    }

    /**
     * json 请求
     *
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param param      请求参数
     * @param header     请求header
     * @param <V>
     * @return
     */
    public static <V> V post(String url, Object param, Class<V> resultType, Map<String, String> header) {

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(param), header(header, MediaType.APPLICATION_JSON_UTF8));
        return RESTTEMPLATE.postForObject(url, requestEntity, resultType);
    }

    /**
     * form 表单请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String postForm(String url, MultiValueMap<String, Object> param) {

        return postForm(url, param, String.class, new HashMap<>());
    }

    /**
     * form 表单请求
     *
     * @param url
     * @param param
     * @param header
     * @return
     */
    public static String postForm(String url, MultiValueMap<String, Object> param, Map<String, String> header) {

        return postForm(url, param, String.class, header);
    }

    /**
     * form 表单请求
     *
     * @param url
     * @param param
     * @param resultType
     * @param header
     * @param <V>
     * @return
     */
    public static <V> V postForm(String url, MultiValueMap<String, Object> param, Class<V> resultType, Map<String, String> header) {

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(param, header(header, MediaType.MULTIPART_FORM_DATA));
        return RESTTEMPLATE.postForObject(url, requestEntity, resultType);
    }

    private static HttpHeaders header(Map<String, String> header, MediaType contentType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.add("Accept", contentType.toString());
        if (MapUtils.isNotEmpty(header)) {
            header.forEach(headers::add);
        }
        return headers;
    }
}
