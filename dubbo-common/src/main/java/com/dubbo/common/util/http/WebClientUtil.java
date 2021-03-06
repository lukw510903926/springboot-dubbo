package com.dubbo.common.util.http;

import com.dubbo.common.util.log.LogUtils;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : lukew
 * @createTime : 2018/4/13 23:29
 * @email : 13507615840@163.com
 * @gitHub : https://github.com/lukw510903926
 * @description :
 */
@Slf4j
public class WebClientUtil {

    private static final Duration TIME_OUT = Duration.ofMillis(3000);

    private static final MediaType MEDIA_TYPE = MediaType.APPLICATION_JSON;

    private static final MediaType MULTIPART_FORM = MediaType.MULTIPART_FORM_DATA;

    /**
     * @param parameter  请求参数
     * @param url        请求路径
     * @param resultType 返回结果类型
     * @return
     */
    public static <V> V post(Object parameter, String url, Class<V> resultType) {

        return post(uri(url, HttpMethod.POST), parameter, MEDIA_TYPE, resultType);
    }

    /**
     * @param parameter  请求参数
     * @param url        请求路径
     * @param header     请求头
     * @param resultType 返回结果类型
     * @return
     */
    public static <V> V post(Object parameter, String url, Map<String, String> header, Class<V> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.POST);
        header(uri, header);
        return post(uri, parameter, MEDIA_TYPE, resultType);
    }

    /**
     * @param uri
     * @param parameter
     * @param mediaType
     * @param resultType
     * @param <V>
     * @return
     */
    private static <V> V post(RequestBodySpec uri, Object parameter, MediaType mediaType, Class<V> resultType) {

        return uri.contentType(mediaType).body(Mono.justOrEmpty(parameter), Object.class).retrieve()
                .bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }

    /**
     * @param parameter
     * @param url
     * @param inputStream
     * @param fileName
     * @param resultType
     * @param <V>
     * @return
     */
    public static <V> V postMultipart(Map<String, Object> parameter, String url, InputStream inputStream, String fileName, Class<V> resultType) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part(fileName, inputStream);
        if (MapUtils.isNotEmpty(parameter)) {
            parameter.forEach(builder::part);
        }
        return post(uri(url, HttpMethod.POST), builder.build(), MULTIPART_FORM, resultType);
    }

    /**
     * @param url
     * @param resultType
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Class<V> resultType) {

        return uri(url, HttpMethod.GET).retrieve().bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }

    /**
     * @param url
     * @param header
     * @param resultType
     * @param <V>
     * @return
     */
    public static <V> V get(String url, Map<String, String> header, Class<V> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.GET);
        header(uri, header);
        return uri.retrieve().bodyToMono(resultType).blockOptional(TIME_OUT).orElse(null);
    }

    /**
     * @param url
     * @param method
     * @return
     */
    private static RequestBodySpec uri(String url, HttpMethod method) {

        return WebClient.create().method(method).uri(url);
    }

    /**
     * @param exchange
     * @param url
     * @return
     */
    public static Mono<Void> exchangeRequest(ServerWebExchange exchange, String url) {

        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = Optional.ofNullable(request.getMethod()).orElse(HttpMethod.GET);
        WebClient.RequestBodySpec requestBodySpec = WebClient.create().method(method).uri(url);
        ServerHttpResponse response = exchange.getResponse();
        RequestBodySpec headers = requestBodySpec.headers(httpHeaders -> {
            httpHeaders.addAll(exchange.getRequest().getHeaders());
            httpHeaders.remove(HttpHeaders.HOST);
        }).contentType(MEDIA_TYPE);
        WebClient.RequestHeadersSpec<?> headersSpec = !requiresBody(method) ? headers : headers.body(BodyInserters.fromDataBuffers(request.getBody()));
        return headersSpec.exchange()
                .doOnError(e -> LogUtils.error(log, e::getMessage))
                .timeout(TIME_OUT)
                .flatMap(e -> response.writeWith(e.body(BodyExtractors.toDataBuffers())));
    }

    private static boolean requiresBody(HttpMethod method) {
        switch (method) {
            case PUT:
            case POST:
            case PATCH:
                return true;
            default:
                return false;
        }
    }

    private static void header(RequestBodySpec uri, Map<String, String> header) {

        if (MapUtils.isNotEmpty(header)) {
            header.forEach(uri::header);
        }
    }
}
