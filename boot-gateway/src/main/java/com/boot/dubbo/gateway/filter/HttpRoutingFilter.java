package com.boot.dubbo.gateway.filter;

import com.dubbo.common.util.log.LogUtils;
import java.time.Duration;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 参考spring-cloud-gateway  WebClientWriteResponseFilter WebClientHttpRoutingFilter
 * @since : 2020-01-29 14:38
 */
@Slf4j
@Component
public class HttpRoutingFilter implements WebFilter {

    private static final String PROXY_HOST = "http://localhost:8090";

    private static final Duration TIME_OUT = Duration.ofMillis(3000);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain webFilterChain) {

        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        log.info(" requestPath : {} requestUri : {}", request.getPath(), requestUri);
        HttpMethod method = Optional.ofNullable(request.getMethod()).orElse(HttpMethod.GET);
        String query = request.getURI().getQuery();
        if (StringUtils.isNoneBlank(query)) {
            requestUri += "?" + query;
        }
        WebClient.RequestBodySpec requestBodySpec = WebClient.create().method(method).uri(PROXY_HOST + requestUri);
        return handleRequestBody(requestBodySpec, exchange);
    }

    private Mono<Void> handleRequestBody(final WebClient.RequestBodySpec requestBodySpec, final ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        return requestBodySpec.headers(httpHeaders -> {
            httpHeaders.addAll(request.getHeaders());
            httpHeaders.remove(HttpHeaders.HOST);
        }).contentType(buildMediaType(exchange))
                .body(BodyInserters.fromDataBuffers(request.getBody()))
                .exchange()
                .doOnError(e -> LogUtils.error(log, e::getMessage))
                .timeout(TIME_OUT)
                .flatMap(e -> response.writeWith(e.body(BodyExtractors.toDataBuffers())));
    }

    private MediaType buildMediaType(final ServerWebExchange exchange) {
        return MediaType.valueOf(Optional.ofNullable(exchange
                .getRequest()
                .getHeaders().getFirst(HttpHeaders.CONTENT_TYPE))
                .orElse(MediaType.APPLICATION_JSON_VALUE));
    }
}
