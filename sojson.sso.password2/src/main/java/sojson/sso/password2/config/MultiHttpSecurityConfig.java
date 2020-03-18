package sojson.sso.password2.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * https://www.jianshu.com/p/3bbea3e2a5c6
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.regexMatcher("^(?!/password).*$").formLogin().loginProcessingUrl("/password/login").and().logout()
					/**
					 * “在CSRF功能开启的情况下使用GET去logout”
					 * <p>
					 * refer to:
					 * "https://blog.csdn.net/mrleeyongsheng/article/details/78886184"
					 */
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).and()
					/**
					 * 源码CsrfFilter.class规定，如果http method只限于"GET", "HEAD",
					 * "TRACE", "OPTIONS"这几种，不校验csrf token，否则校验。
					 */
					//.csrf().disable()
					.authorizeRequests().anyRequest().authenticated();
		}

	}

	@EnableOAuth2Sso
	@Configuration
	public static class SsoWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.regexMatcher("^(?=/password).*$").authorizeRequests().anyRequest().authenticated();
		}

	}

}
