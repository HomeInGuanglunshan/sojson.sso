package sojson.sso.authorization.code2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${server.servlet.session.cookie.name:SESSION}")
	String COOKIE_NAME;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated();
		http.logout().logoutUrl("/u/logout.shtml").logoutSuccessUrl("http://localhost:20200/logout")
				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies(COOKIE_NAME);
	}

}
