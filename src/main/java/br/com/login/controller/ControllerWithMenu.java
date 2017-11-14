package br.com.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import br.com.login.dto.MensagemRetornoDTO;
import br.com.login.enumerator.TipoMensagem;

public abstract class ControllerWithMenu implements HandlerExceptionResolver {

	@Autowired
	private LoginSessionBean loginSessionBean;

	@ModelAttribute("loginSessionBean")
	public LoginSessionBean getLoginSessionBean() {
		return loginSessionBean;
	}
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {

		ModelAndView view = new ModelAndView("redirect:" + request.getRequestURI());
		
		MensagemRetornoDTO mensagemRetornoDTO = new MensagemRetornoDTO(TipoMensagem.ERRO,
				"Ocorreu um erro, tente novamente mais tarde.", "Higia");

		FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
		
		if (outputFlashMap != null) {
			outputFlashMap.put("messageReturn", mensagemRetornoDTO);
		}
		
		return view;
	}

}
