package sojson.sso.common.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		for (; headerNames.hasMoreElements();) {
			String name = headerNames.nextElement();
			template.header(name, request.getHeader(name));
		}
	}

}
