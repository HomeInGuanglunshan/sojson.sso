package sojson.sso.authorization.server.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

/**
 * SsoSecurityConfig和WebSecurityConfig的结合体
 */
//@EnableWebSecurity
public class MultiSecurityConfig {

//	@Configuration
//	@Order(1)
	public static class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

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

//	@Configuration
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Value("${server.servlet.session.cookie.name:SESSION}")
		String COOKIE_NAME;

		/**
		 * 必须<? extends Session>才能正确注入，不能是<Session>，否则......类型不匹配？
		 */
		@Autowired
		FindByIndexNameSessionRepository<? extends Session> sessionRepository;

		@Override
		@Bean
		public UserDetailsService userDetailsService() {
			InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
			manager.createUser(User.withUsername("cralor").password(new BCryptPasswordEncoder().encode("123"))
					.roles("USER").build());
			manager.createUser(User.withUsername("admin").password(new BCryptPasswordEncoder().encode("123456"))
					.roles("ADMIN", "USER").build());
//			manager.createUser(
//					User.withUsername("admin").password(new BCryptPasswordEncoder().encode("123")).roles("ADMIN").build());
			return manager;
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().defaultSuccessUrl("/").and().authorizeRequests().anyRequest().authenticated()
					//.and().cors() // 好像注释掉也没有什么影响
					.and().csrf().disable();
			http.logout()
					//.logoutSuccessUrl("/") // 默认跳转到/login?logout
					.clearAuthentication(true).invalidateHttpSession(true).deleteCookies(COOKIE_NAME);
			http.logout().logoutSuccessHandler((request, response, authentication) -> {
				// 如果先前已经logout过，再请求一次/logout的话，authentication == null
				if (null == authentication) {
					return;
				}
				Map<String, ? extends Session> sessions = sessionRepository.findByIndexNameAndIndexValue(
						FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, authentication.getName());
				// 此处是RedisSession，不是HttpSession，前者不能通过强转型成为后者
				for (String sessionId : sessions.keySet()) {
					sessionRepository.deleteById(sessionId);
				}
				new DefaultRedirectStrategy().sendRedirect(request, response, "/");
			});
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
	}
}