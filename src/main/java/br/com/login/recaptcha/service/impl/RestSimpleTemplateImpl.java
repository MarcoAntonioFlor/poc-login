package br.com.login.recaptcha.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.login.recaptcha.service.RestTemplateService;

@Component
@Profile({ "dev", "hm", "prod" })
public class RestSimpleTemplateImpl implements RestTemplateService {

	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
