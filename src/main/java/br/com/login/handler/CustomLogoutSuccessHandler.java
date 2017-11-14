package br.com.login.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private static String VIEW = "login";
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if(authentication != null) {
	        SecurityContextHolder.clearContext();
	        if(request.getSession() != null) {
	        	request.getSession().invalidate();
	        }
			
		}
		response.setStatus(HttpStatus.OK.value());
		response.sendRedirect(VIEW);
	}
}
