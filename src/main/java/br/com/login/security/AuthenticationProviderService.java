package br.com.login.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.login.business.LoginBO;
import br.com.login.controller.LoginSessionBean;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {
	
	@Autowired
	private LoginSessionBean loginSessionBean;
	
	@Autowired
	private LoginBO loginBO;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		recaptchaValidado();
		
		String login = auth.getName();
		String senha = auth.getCredentials().toString();

		if (!senha.isEmpty() || auth.getName().isEmpty()) {
			Boolean autentica = loginBO.autentica(auth.getName(), auth.getCredentials().toString());

			if (autentica) {

				Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

				UserSecurity user = new UserSecurity();
				user.setUsername(login);
				user.setPassword(senha);

				return new UsernamePasswordAuthenticationToken(user, senha, authorities);
			} else {
				throw new BadCredentialsException("Este usuário está desativado.");
			}
		}
		throw new BadCredentialsException("Usuário ou senha não foram informados.");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private void recaptchaValidado(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String keyRecaptcha = request.getParameter("g-recaptcha-response");
		loginSessionBean.setKeyReCaptcha(keyRecaptcha);
		if(keyRecaptcha.isEmpty()){
			throw new BadCredentialsException("Recaptcha não foi válidado.");
		}
	}
}
