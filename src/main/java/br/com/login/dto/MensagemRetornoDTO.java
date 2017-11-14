package br.com.login.dto;

import br.com.login.enumerator.TipoMensagem;
import lombok.Data;

@Data
public class MensagemRetornoDTO {
	
	private String mensagem;
	private String titulo;
	private TipoMensagem tipoMensagem;
	private Object objetoRetorno;
	
	public MensagemRetornoDTO(TipoMensagem tipoMensagem, Object objetoRetorno){
		this.tipoMensagem = tipoMensagem;
		this.objetoRetorno = objetoRetorno;
	}
	
	public MensagemRetornoDTO(TipoMensagem tipoMensagem){
		this.tipoMensagem = tipoMensagem;
	}
	
	public MensagemRetornoDTO(TipoMensagem tipoMensagem, String mensagem, String titulo){
		this.tipoMensagem = tipoMensagem;
		this.mensagem = mensagem;
		this.titulo = titulo;
	}
	
	public MensagemRetornoDTO(TipoMensagem tipoMensagem, String mensagem, String titulo, Object objetoRetorno){
		this.tipoMensagem = tipoMensagem;
		this.mensagem = mensagem;
		this.objetoRetorno = objetoRetorno;
		this.titulo = titulo;
	}
}
