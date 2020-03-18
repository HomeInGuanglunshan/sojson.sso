package sojson.sso.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import feign.RequestTemplate;

/**
 * https://www.jianshu.com/p/378b0e7cd9d5
 */
public class OAuth2FeignRequestInterceptor2 extends OAuth2FeignRequestInterceptor {

	public OAuth2FeignRequestInterceptor2(OAuth2ClientContext oAuth2ClientContext,
			OAuth2ProtectedResourceDetails resource) {
		super(oAuth2ClientContext, resource);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OAuth2ClientContext context;

	@Override
	public void apply(RequestTemplate template) {

		if (context.getAccessToken() != null && context.getAccessToken().getValue() != null
				&& OAuth2AccessToken.BEARER_TYPE.equalsIgnoreCase(context.getAccessToken().getTokenType())) {
			template.header("Authorization",
					String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, context.getAccessToken().getValue()));
		}

	}

}
