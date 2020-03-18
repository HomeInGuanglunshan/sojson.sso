package sojson.sso.password2.component;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

//@Component
public class MyResourceOwnerPasswordAccessTokenProvider extends ResourceOwnerPasswordAccessTokenProvider {

	@Value("${spring.cloud.client.ip-address}")
	String ipAddress;

	@Value("${server.port}")
	String port;

	@Override
	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
			throws UserRedirectRequiredException, AccessDeniedException, OAuth2AccessDeniedException {
		if (StringUtils.isBlank(request.getFirst("username")) || StringUtils.isBlank(request.getFirst("password"))) {
			String redirectUrl = "http://" + ipAddress + ":" + port + "/password/login";
			throw new UserRedirectRequiredException(redirectUrl, Collections.emptyMap());
		}
		return super.obtainAccessToken(details, request);
	}

}
