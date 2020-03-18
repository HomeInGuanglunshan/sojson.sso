package sojson.sso.authorization.server.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

	@RequestMapping(value = "userInfo", produces = { "application/json" })
	public Principal userInfo(Principal principal) {
		return principal;
	}

}
