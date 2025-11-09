package com.banking.system.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		String fullUri = queryString != null ? uri + "?" + queryString : uri;
		
		log.info("Request: {} {} from {}", method, fullUri, request.getRemoteAddr());
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
	                           Object handler, Exception ex) {
		int status = response.getStatus();
		String method = request.getMethod();
		String uri = request.getRequestURI();
		
		if (ex != null) {
			log.error("Request failed: {} {} - Status: {} - Error: {}", 
				method, uri, status, ex.getMessage());
		} else {
			log.info("Response: {} {} - Status: {}", method, uri, status);
		}
	}
}

