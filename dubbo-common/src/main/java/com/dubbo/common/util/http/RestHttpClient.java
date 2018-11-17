package com.dubbo.common.util.http;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

/**
 * @version V1.0
 * @Description: rest http 请求
 * @author: yangqi
 * @email : yangqi@ywwl.com
 * @date: 2018年10月10日 下午7:05:47
 */
public class RestHttpClient {

    private static RestTemplate restTemplate = new RestTemplate();

    private RestHttpClient(){}

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(15000);
        requestFactory.setReadTimeout(10000);
        restTemplate.setRequestFactory(requestFactory);
    }

    public static String get(String url){

        return get(url,String.class);
    }

    /**
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Class<V> resultType) {

        return restTemplate.getForObject(url, resultType);
    }

    /**
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param header     请求header
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Class<V> resultType, Map<String, String> header) {

        HttpEntity<String> requestEntity = new HttpEntity<>(header(header));
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, resultType).getBody();
    }

    public static String post(String url,Object param){

        return post(url,param,String.class);
    }

    /**
     * @param url        请求参数
     * @param resultType 返回结果类型
     * @param param      请求参数
     * @param <V>
     * @return
     */
    public static <V> V post(String url, Object param,Class<V> resultType) {

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(param), header(new HashMap<>()));
        return restTemplate.postForObject(url, requestEntity, resultType);
    }

    /**
     * @param url        请求地址
     * @param resultType 返回结果类型
     * @param param      请求参数
     * @param header     请求header
     * @param <V>
     * @return
     */
    public static <V> V post(String url, Object param,Class<V> resultType, Map<String, String> header) {

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(param), header(header));
        return restTemplate.postForObject(url, requestEntity, resultType);
    }

    private static HttpHeaders header(Map<String, String> header) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8.toString());
        if (MapUtils.isNotEmpty(header)) {
            header.forEach(headers::add);
        }
        return headers;
    }
}
