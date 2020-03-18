package sojson.sso.password2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${server.servlet.session.cookie.name:SESSION}")
	String COOKIE_NAME;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().formLogin().loginProcessingUrl("/password/login").and().authorizeRequests().anyRequest()
				.authenticated();

		http.logout().logoutUrl("/u/logout.shtml").clearAuthentication(true).invalidateHttpSession(true)
				.deleteCookies(COOKIE_NAME);
	}

}
