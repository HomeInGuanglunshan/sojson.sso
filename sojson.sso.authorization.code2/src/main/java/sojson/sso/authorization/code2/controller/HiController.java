package sojson.sso.authorization.code2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hi")
public class HiController {

	@RequestMapping("/{name}")
	@ResponseBody
	public String hi(@PathVariable("name") String name) {
		return "hi " + name + "!";
	}

}
