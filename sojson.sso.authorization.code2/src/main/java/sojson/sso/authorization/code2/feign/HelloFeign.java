package sojson.sso.authorization.code2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sojson.sso.common.config.FeignConfiguration;

@FeignClient(value = "sojson.sso.authorization.code", configuration = FeignConfiguration.class)
public interface HelloFeign {

	@PostMapping("/hello/{name}")
	String hello(@PathVariable("name") String name);

}
