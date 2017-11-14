package br.com.login.recaptcha.service.impl;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import br.com.login.exception.ReCaptchaInvalidException;
import br.com.login.exception.ReCaptchaUnavailableException;
import br.com.login.recaptcha.CaptchaSettings;
import br.com.login.recaptcha.service.CaptchaService;
import br.com.login.recaptcha.service.RestTemplateService;
import br.com.login.security.GoogleResponse;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private static final Logger log = Logger.getLogger(CaptchaServiceImpl.class);

	@Autowired
	private CaptchaSettings captchaSettings;

	@Autowired
	private RestTemplateService restTemplate;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ReCaptchaAttemptServiceImpl reCaptchaAttemptService;

	@Override
	public void processResponse(final String response) throws ReCaptchaInvalidException {
		if (reCaptchaAttemptService.isBlocked(getClientIP())) {
			log.error(messageSource.getMessage("modal.recaptcha.excedeu.numero.tentativa", null,
					LocaleContextHolder.getLocale()));
			throw new ReCaptchaInvalidException(messageSource.getMessage("modal.recaptcha.excedeu.numero.tentativa",
					null, LocaleContextHolder.getLocale()));
		}

		final URI verifyUri = URI.create(String.format("%ssecret=%s&response=%s&remoteip=%s", captchaSettings.getUrl(),
				getReCaptchaSecret(), response, getClientIP()));

		log.info("Ip request reCaptcha: " + getClientIP());

		try {
			final ResponseEntity<GoogleResponse> googleResponse = restTemplate.getRestTemplate().exchange(verifyUri,
					HttpMethod.GET, null, GoogleResponse.class);

			if (!googleResponse.getBody().isSuccess()) {
				if (googleResponse.getBody().hasClientError())
					reCaptchaAttemptService.reCaptchaFailed(getClientIP());

				log.error(messageSource.getMessage("modal.recaptcha.invalido", null, LocaleContextHolder.getLocale()));
				throw new ReCaptchaInvalidException(
						messageSource.getMessage("modal.recaptcha.invalido", null, LocaleContextHolder.getLocale()));
			}
		} catch (final RestClientException ex) {
			log.error(messageSource.getMessage("modal.recaptcha.indisponivel", null, LocaleContextHolder.getLocale()));
			throw new ReCaptchaUnavailableException(
					messageSource.getMessage("modal.recaptcha.indisponivel", null, LocaleContextHolder.getLocale()),
					ex);
		}
		reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
	}

	@Override
	public String getReCaptchaSite() {
		return captchaSettings.getSite();
	}

	@Override
	public String getReCaptchaSecret() {
		return captchaSettings.getSecret();
	}

	private String getClientIP() {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null)
			return request.getRemoteAddr();
		return xfHeader.split(",")[0];
	}

}
