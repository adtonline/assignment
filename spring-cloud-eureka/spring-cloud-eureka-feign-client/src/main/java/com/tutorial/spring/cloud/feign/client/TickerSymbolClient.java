package com.tutorial.spring.cloud.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("spring-cloud-eureka-client")
public interface TickerSymbolClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/v2/ping")
	public String ping();
}
