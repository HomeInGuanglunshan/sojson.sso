package sojson.sso.authorization.code.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sojson.sso.common.config.FeignConfiguration;

@FeignClient(value = "sojson.sso.authorization.code2", configuration = FeignConfiguration.class)
public interface HiFeign {

	@PostMapping("/hi/{name}")
	String hi(@PathVariable("name") String name);

}
