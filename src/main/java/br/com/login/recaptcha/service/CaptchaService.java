package br.com.login.recaptcha.service;

import br.com.login.exception.ReCaptchaInvalidException;

public interface CaptchaService {

	void processResponse(final String response) throws ReCaptchaInvalidException;

	String getReCaptchaSite();

	String getReCaptchaSecret();
}
