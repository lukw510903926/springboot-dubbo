package com.boot.dubbo.gateway.filter;

import com.dubbo.common.util.http.RestHttpClient;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-01-29 14:38
 */
@Slf4j
@Component
public class GatewayFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain webFilterChain) {
        String requestUri = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info(" requestPath : {}", exchange.getRequest().getPath());
        log.info("requestUri {}", requestUri);
        String result = RestHttpClient.get("http://localhost:8090/gson/format");
        return writeMsg(exchange, result);
    }

    public static Mono<Void> writeMsg(ServerWebExchange exchange, String msg) {

        ServerHttpResponse response = exchange.getResponse();
        exchange.getAttributes().put("original_response_content_type", MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        response.setStatusCode(HttpStatus.OK);
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}
