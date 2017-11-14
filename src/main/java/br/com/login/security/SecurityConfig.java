package br.com.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.login.handler.CustomAccessDeniedHandler;
import br.com.login.handler.CustomFailureHandler;
import br.com.login.handler.CustomLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProviderService authProvider;

	@Autowired
	private CustomFailureHandler failureHandler;

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/css/**", "/fonts/**", "/images/**", "/js/**", "/scss/**").permitAll()
		.antMatchers("/user/registrationCaptcha/**").permitAll()
		.antMatchers("/").permitAll()
		.antMatchers("/uploadNotaFiscal/**").hasRole("UPLOAD_NOTA_FISCAL")
		.antMatchers("/uploadResumoMedico/**").hasRole("UPLOAD_RESUMO_MEDICO")
		.antMatchers("/cadastroMedico/**", "/cadastroEmpresaMedica/**", "/consultarMedico/**", "/consultarEmpresaMedica/**").hasRole("CADASTROS_MEDICO_EMPRESA")
		.antMatchers("/consultarDemonstrativoEmpresa/**").hasRole("CONSULTA_DEMONSTRATIVO_EMPRESA")
		.antMatchers("/health-check").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/login/loginSecuritySucess", true).permitAll()
		.failureHandler(failureHandler)
		.and()
		.logout()
		.logoutUrl("/logout").permitAll()
		.logoutSuccessHandler(customLogoutSuccessHandler)
		.and()
		.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler)
		.and()
		.headers()
		.defaultsDisabled()
		.frameOptions()
		.sameOrigin()
		.cacheControl();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
}