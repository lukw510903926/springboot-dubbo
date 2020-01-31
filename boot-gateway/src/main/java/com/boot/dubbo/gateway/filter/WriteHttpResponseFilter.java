package com.boot.dubbo.gateway.filter;

import com.boot.dubbo.gateway.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 将http 请求的结果写回到response中
 * 参考spring-cloud-gateway  WebClientWriteResponseFilter WebClientHttpRoutingFilter
 * @since : 2020-01-31 21:12
 */
@Slf4j
public class WriteHttpResponseFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // NOTICE: nothing in "pre" filter stage as CLIENT_RESPONSE_ATTR is not added until the WebHandler is run
        return chain.filter(exchange).doOnError(throwable -> cleanup(exchange))
                .then(Mono.defer(() -> {
                    ClientResponse clientResponse = exchange.getAttribute(Constants.CLIENT_RESPONSE_ATTR);
                    if (clientResponse == null) {
                        return Mono.empty();
                    }
                    log.trace("WebClientWriteResponseFilter start");
                    ServerHttpResponse response = exchange.getResponse();
                    return response
                            .writeWith(clientResponse.body(BodyExtractors.toDataBuffers()))
                            .log("webClient response")
                            .doOnCancel(() -> cleanup(exchange));
                }));
    }

    private void cleanup(ServerWebExchange exchange) {
        ClientResponse clientResponse = exchange.getAttribute(Constants.CLIENT_RESPONSE_ATTR);
        if (clientResponse != null) {
            clientResponse.bodyToMono(Void.class).subscribe();
        }
    }
}
