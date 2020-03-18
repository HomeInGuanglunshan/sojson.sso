package sojson.sso.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import feign.Logger;
import feign.RequestInterceptor;

/**
 * https://www.jianshu.com/p/378b0e7cd9d5
 */
public class FeignConfiguration {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public RequestInterceptor oAuth2RequestInterceptor(OAuth2ProtectedResourceDetails resource,
			OAuth2ClientContext oauth2Context) {
		return new OAuth2FeignRequestInterceptor2(oauth2Context, resource);
	}

}
