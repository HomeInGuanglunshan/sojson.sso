package sojson.sso.client.credentials.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;

@Component
public class MyUserInfoRestTemplateFactory implements UserInfoRestTemplateFactory {

	@Autowired
	OAuth2ClientContext oAuth2ClientContext;

	@Autowired
	OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

	@Override
	public OAuth2RestTemplate getUserInfoRestTemplate() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		BeanUtils.copyProperties(oAuth2ProtectedResourceDetails, resourceDetails, "grantType");

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);

		template.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());

		return template;
	}

}
