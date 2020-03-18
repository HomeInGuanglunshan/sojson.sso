package sojson.sso.authorization.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping("/{name}")
	@ResponseBody
	public String hello(@PathVariable("name") String name) {
		return "hello " + name + "!";
	}

}
