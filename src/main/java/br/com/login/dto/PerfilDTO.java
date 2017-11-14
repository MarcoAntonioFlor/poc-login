package br.com.login.dto;

import java.util.List;

import lombok.Data;

@Data
public class PerfilDTO {

	private Long id;
	private String descricao;
	private List<PrivilegioDTO> privilegios;
	
}
