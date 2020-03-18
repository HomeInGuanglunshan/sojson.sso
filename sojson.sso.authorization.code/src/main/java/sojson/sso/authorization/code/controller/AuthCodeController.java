package sojson.sso.authorization.code.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sojson.sso.authorization.code.feign.HiFeign;

@Controller
public class AuthCodeController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	HiFeign hiFeign;

	@RequestMapping("setSessionAttr/{name}/{value}")
	@ResponseBody
	public Object setSessionAttr(@PathVariable("name") String name, @PathVariable("value") Object value) {
		request.getSession().setAttribute(name, value);
		return name;
	}

	@RequestMapping("getSessionAttr/{name}")
	@ResponseBody
	public Object getSessionAttr(@PathVariable("name") String name) {
		return request.getSession().getAttribute(name);
	}

	@RequestMapping("pleaseSayHiTo/{name}")
	@ResponseBody
	public String pleaseSayHiTo(@PathVariable("name") String name) {
		return hiFeign.hi(name);
	}

	@Autowired
	OAuth2ClientContext oAuth2ClientContext;

	@RequestMapping("getAccessToken")
	@ResponseBody
	public Object getAccessToken() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		resourceDetails.setAccessTokenUri("http://localhost:20200/oauth/token");
		resourceDetails.setScope(Arrays.asList("all"));
//		resourceDetails.setId("resource");
		resourceDetails.setClientId("Password");
		resourceDetails.setClientSecret("20500");
		resourceDetails.setUsername("admin");
		resourceDetails.setPassword("123456");

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);

		return template.getAccessToken();
	}

}
