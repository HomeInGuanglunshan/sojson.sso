package sojson.sso.implicit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.stereotype.Component;

@Component
public class MyUserInfoRestTemplateFactory implements UserInfoRestTemplateFactory {

	@Autowired
	OAuth2ClientContext oAuth2ClientContext;

	@Override
	public OAuth2RestTemplate getUserInfoRestTemplate() {
		ImplicitResourceDetails resourceDetails = new ImplicitResourceDetails();
		resourceDetails.setUserAuthorizationUri("http://localhost:20200/oauth/authorize");
//		resourceDetails.setAccessTokenUri(
//				"http://localhost:20200/oauth/authorize?response_type={response_type}&client_id={client_id}&redirect_uri={redirect_uri}");
		resourceDetails.setAccessTokenUri("http://localhost:20200/oauth/authorize");
		resourceDetails.setClientId("Implicit");

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);

		template.setAccessTokenProvider(new ImplicitAccessTokenProvider());

		return template;
	}

}
