package com.zzc;

import com.zzc.model.entity.InterfaceInfo;
import com.zzc.model.entity.User;
import com.zzc.provider.InterfaceInfoService;
import com.zzc.provider.UserInterfaceInvokeService;
import com.zzc.provider.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zzc.utils.SignUtil.getSign;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private UserService userService;

    @DubboReference
    private InterfaceInfoService interfaceInfoService;

    @DubboReference
    private UserInterfaceInvokeService userInterfaceInvokeService;

    public static final List<String> WHITE_LIST = Collections.singletonList("127.0.0.1");

    public static final String HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //日志打印
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识:" + request.getId());
        String url = request.getPath().value();
        log.info("请求路径:" + url);
        String method = request.getMethod().toString();
        log.info("请求方法:" + method);
        log.info("请求参数:" + request.getQueryParams());
        String address = request.getRemoteAddress().getHostString();
        log.info("请求来源地址:" + address);
        log.info("custom global filter");
        //黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!WHITE_LIST.contains(address)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        long currentTime = System.currentTimeMillis() / 1000;
        long fiveMin = 5 * 60;
        if (currentTime < Long.parseLong(timestamp) || (currentTime - Long.parseLong(timestamp)) >= fiveMin) {
            return handlerNoAuth(response);
        }
        User userByAccount = userService.getUserInvokeInfo(accessKey);
        if (userByAccount == null) {
            return handlerNoAuth(response);
        }
        if (sign != null && !sign.equals(getSign(body, userByAccount.getSecretKey()))) {
            return handlerNoAuth(response);
        }
        InterfaceInfo byUrlAndMethod = interfaceInfoService.getByUrlAndMethod(HOST + url, method);
        if (byUrlAndMethod == null) {
            return handlerNoAuth(response);
        }
        return responseLog(exchange, chain, userByAccount.getId(), byUrlAndMethod.getId());
    }

    public Mono<Void> responseLog(ServerWebExchange exchange, GatewayFilterChain chain, long userId, long interfaceId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {

                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {

                                        //次数加减
                                try {
                                    userInterfaceInvokeService.invokeAfter(userId, interfaceId);
                                } catch (Exception e) {
                                    log.error(e.getMessage());
                                }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handlerNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}