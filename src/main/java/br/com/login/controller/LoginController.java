package br.com.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import br.com.login.business.LoginBO;
import br.com.login.dto.LoginDTO;
import br.com.login.dto.MensagemRetornoDTO;
import br.com.login.enumerator.TipoMensagem;
import br.com.login.security.UserSecurity;

@RequestMapping({ "/login", "/*" })
@Controller
public class LoginController {

	private static final String VIEW = "/login";
	private static final String REDIRECT_VIEW = "redirect:/login";

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginBO loginBO;

	@Autowired
	private LoginSessionBean loginSessionBean;

	@ModelAttribute("loginDTO")
	public LoginDTO getLoginDTO() {
		return new LoginDTO();
	}

	@GetMapping
	public ModelAndView init(final LoginDTO loginDTO) {
		return new ModelAndView(VIEW);
	}

	@GetMapping("/loginSecuritySucess")
	public ModelAndView loginSucess(final Authentication authentication, final HttpServletRequest request) {
		try {
			final UserSecurity user = (UserSecurity) authentication.getPrincipal();
			loginBO.preencherDadosDeSessao(user.getUsername());
		} catch (final Exception e) {
			logger.error("loginSucess", e);
			final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);

			if (outputFlashMap != null)
				outputFlashMap.put("messageReturn", new MensagemRetornoDTO(TipoMensagem.ERRO,
						"Erro nas informações do usuário", "Autenticação", Boolean.FALSE));
			return new ModelAndView(REDIRECT_VIEW);
		}
		return new ModelAndView("index");
	}

	@PostMapping
	public String loginError(@Valid final LoginDTO loginDTO, final BindingResult bindingResult, final Model model,
			HttpServletRequest request) {
		
		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
		
		if (bindingResult.hasErrors()) {
			final StringBuilder mensagens = new StringBuilder();

			if (loginDTO.getUsername().isEmpty()) {
				mensagens.append("Informe o usuário");
				outputFlashMap.put("messageReturn",
						new MensagemRetornoDTO(TipoMensagem.ERRO, mensagens.toString(), "Login"));
				return REDIRECT_VIEW;
			}

			bindingResult.getAllErrors().forEach(erro -> mensagens.append(erro.getDefaultMessage() + "\n"));
			outputFlashMap.put("messageReturn",
					new MensagemRetornoDTO(TipoMensagem.ERRO, mensagens.toString(), "Login"));
			outputFlashMap.put("loginDTO", loginDTO);
			return REDIRECT_VIEW;
		}else if(loginSessionBean.getKeyReCaptcha().isEmpty()){
			outputFlashMap.put("messageReturn",
					new MensagemRetornoDTO(TipoMensagem.ERRO,"Informe que você não é um robô", "Login"));
			return REDIRECT_VIEW;
		}

		outputFlashMap.put("messageReturn", new MensagemRetornoDTO(TipoMensagem.ERRO,
				"Falha na autenticação, usuário ou senha inválidos", "Autenticação", Boolean.FALSE));
		outputFlashMap.put("loginDTO", loginDTO);
		return REDIRECT_VIEW;
	}
}
