package br.com.login.recaptcha.service;

public interface ReCaptchaAttemptService {

	void reCaptchaSucceeded(String key);

	void reCaptchaFailed(String key);

	boolean isBlocked(String key);

}
