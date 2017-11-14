package br.com.login.recaptcha.service;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateService {

	RestTemplate getRestTemplate();

}
