package br.com.login.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.login.controller.LoginSessionBean;
import io.swagger.client.api.AutenticacaoControllerApi;
import io.swagger.client.api.UsuarioControllerApi;
import io.swagger.client.model.UsuarioVO;

/**
 * Classe de negocio de Login
 *
 * @author Marco Antonio
 */
@Component
public class LoginBO {

	Logger logger = LoggerFactory.getLogger(LoginBO.class);
	
	
	@Autowired
	private AutenticacaoControllerApi autenticacaoControllerApi;

	@Autowired
	private LoginSessionBean loginSessionBean;
	
	@Autowired
	private UsuarioControllerApi usuarioControllerApi;

	/**
	 * Autentica no ldap atraves da API
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean autentica(final String login, final String password) {
		try {
			return (Boolean) autenticacaoControllerApi.autenticaLdapUsingGET(login, password);
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
		return Boolean.FALSE;
	}

	/**
	 * Preenche dados de Sessao do login SessionBean
	 * @param login
	 * @throws Exception
	 */
	public void preencherDadosDeSessao(final String login) {
		final UsuarioVO usuarioVO = recuperaUsuarioPorLogin(login);
		loginSessionBean.setIdUsuario(usuarioVO.getId());
		loginSessionBean.setNome(usuarioVO.getNome());
		loginSessionBean.setLogin(login);
	}

	/**
	 * recupera usuario por login
	 * @param login
	 * @return
	 */
	public UsuarioVO recuperaUsuarioPorLogin(final String login) {
		try {
			return usuarioControllerApi.recuperaUsuarioPorLoginUsingGET(login);
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new UsuarioVO();
	}


}