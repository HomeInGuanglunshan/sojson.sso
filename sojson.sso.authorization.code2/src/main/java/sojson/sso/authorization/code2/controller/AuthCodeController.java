package sojson.sso.authorization.code2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sojson.sso.authorization.code2.feign.HelloFeign;

@Controller
public class AuthCodeController {

	@Autowired
	HttpServletRequest request;

	@Autowired
	HelloFeign helloFeign;

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

	@RequestMapping("pleaseSayHelloTo/{name}")
	@ResponseBody
	public String pleaseSayHelloTo(@PathVariable("name") String name) {
		return helloFeign.hello(name);
	}

}
