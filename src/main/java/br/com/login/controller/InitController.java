package br.com.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/", ""})
public class InitController {
	
	private static String REDIRECT_VIEW = "redirect:/login";
	
	@GetMapping
	public ModelAndView init() {
		
		return new ModelAndView(REDIRECT_VIEW);
	}
	
}
