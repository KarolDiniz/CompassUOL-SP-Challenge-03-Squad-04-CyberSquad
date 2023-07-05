package br.com.compassuol.pb.challenge.msgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/categories/**").uri("lb://msproducts"))
				.route(r -> r.path("/products/**").uri("lb://msproducts"))
				.route(r -> r.path("/users/**").uri("lb://msusers"))
				.route(r -> r.path("/roles/**").uri("lb://msusers"))
				.build();
	}
}