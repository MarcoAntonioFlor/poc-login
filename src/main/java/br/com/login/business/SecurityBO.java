package br.com.login.business;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.login.controller.LoginSessionBean;
import br.com.login.dto.PerfilDTO;
import br.com.login.dto.PrivilegioDTO;
import br.com.login.security.UserSecurity;
import io.swagger.client.api.PerfilControllerApi;
import io.swagger.client.model.PerfilVO;

@Component
public class SecurityBO {

	@Autowired
	private LoginSessionBean loginSessionBean;
	
	@Autowired
	private PerfilControllerApi perfilControllerApi;
	
	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Adiciona privilegios
	 * @param privilegios
	 */
	public void adicionaPrivilegios(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserSecurity user = (UserSecurity)auth.getPrincipal();
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
		if(!recuperaPrivilegios().isEmpty()){
			recuperaPrivilegios().stream().forEach(privilegio -> authorities.add(privilegio));
			user.setAuthorities(authorities);
		}
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),authorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
	private List<PrivilegioDTO> recuperaPrivilegios(){
		PerfilVO perfil = perfilControllerApi.recuperaPerfilCredenciamentoUsingGET(loginSessionBean.getLogin());
		return adicionaPerfil(perfil);
	}
	
	private List<PrivilegioDTO> adicionaPerfil(PerfilVO perfil){
		
		List<PrivilegioDTO> listaPrivilegios = new ArrayList<>();
		if(perfil != null){
			loginSessionBean.setPerfil(modelMapper.map(perfil, PerfilDTO.class));
			PerfilDTO perfilDTO = modelMapper.map(perfil, PerfilDTO.class);
			listaPrivilegios = perfilDTO.getPrivilegios();
		}
		return listaPrivilegios;
	}
}
