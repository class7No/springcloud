package org.springcloud.gateway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 配置Gateway
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes().route(p ->
                p.path("/get") // 请求路径get
                .filters(f -> f.addRequestHeader("hello","world")) //添加头部信息
                .uri("http://httpbin.org:80")) // 路由到 这个服务
                .route(p -> p
                .host("*.hystrix.com") // 请求超时熔断
                .filters(f -> f.hystrix(config -> config.setName("mycmd")))
                .uri("http://httpbin.org:80")) // 跳转的路径
                .build();
    }

}
