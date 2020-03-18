package sojson.sso.authorization.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

	/**
	 * refer to: https://segmentfault.com/a/1190000013531863
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/allLogout")
	public void allLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		new SecurityContextLogoutHandler().logout(request, null, null);
		response.sendRedirect("/");
	}

}
