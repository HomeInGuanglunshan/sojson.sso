package sojson.sso.password2.component;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Component;

@Component
public class MyUserInfoRestTemplateFactory implements UserInfoRestTemplateFactory {

	@Autowired
	OAuth2ClientContext oAuth2ClientContext;

	@Autowired
	OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

//	@Autowired
//	MyResourceOwnerPasswordAccessTokenProvider myResourceOwnerPasswordAccessTokenProvider;

	@Override
	public OAuth2RestTemplate getUserInfoRestTemplate() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		BeanUtils.copyProperties(oAuth2ProtectedResourceDetails, resourceDetails, "grantType");

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);

//		template.setAccessTokenProvider(myResourceOwnerPasswordAccessTokenProvider);
		template.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());

		return template;
	}

}
