package br.com.login.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.login.dto.PerfilDTO;
import lombok.Data;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class LoginSessionBean implements Serializable {

	private static final long serialVersionUID = 2073912428707917050L;
	private Long idUsuario;
	private String nome;
	private String login;
	private String keyReCaptcha;
	private PerfilDTO perfil;
	
	@Value("${project.version}")
	private String version;
}
