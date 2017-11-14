package br.com.login.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class PrivilegioDTO implements GrantedAuthority {
	
	private static final long serialVersionUID = 8818976798109884391L;
	private long id;
	private String nome;
	private String descricao;
	
	@Override
	public String getAuthority() {
		return nome;
	}
	
	
}
