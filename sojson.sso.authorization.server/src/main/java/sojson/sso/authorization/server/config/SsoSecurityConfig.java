package sojson.sso.authorization.server.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Order(99)
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.antMatcher("/oauth/**");

		// 不加permitAll()时，访问/oauth/login，重定向到/oauth/login，无限循环，所以加上。难道和自定义loginPage有关？不自定义时不用这么做
		// login page和login process url默认一致？设置了前者即可？
		http.formLogin().loginPage("/oauth/login").permitAll();

		http.authorizeRequests().anyRequest().authenticated();
	}

}
