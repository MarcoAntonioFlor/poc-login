package br.com.login.business;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.login.dto.PerfilDTO;
import io.swagger.client.api.PerfilControllerApi;
import io.swagger.client.model.PerfilVO;

@Component

public class PerfilBO {
	Logger logger = LoggerFactory.getLogger(PerfilBO.class);

	@Autowired
	private PerfilControllerApi perfilControllerApi;

	private final ModelMapper modelMapper = new ModelMapper();

	public PerfilDTO recuperaPerfilPorLoginMedico(final String login, final Long idEmpresa) {

		PerfilDTO perfil = new PerfilDTO();
		try {
			final PerfilVO perfilVO = perfilControllerApi.recuperaPerfilMedicoUsingGET(login, idEmpresa);

			perfil = modelMapper.map(perfilVO, PerfilDTO.class);

		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
		return perfil;
	}
}
