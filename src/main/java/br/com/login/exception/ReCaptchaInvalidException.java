package br.com.login.exception;

public class ReCaptchaInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReCaptchaInvalidException() {
		super();
	}

	public ReCaptchaInvalidException(final String mensagem, final Throwable causa) {
		super(mensagem, causa);
	}

	public ReCaptchaInvalidException(final String mensagem) {
		super(mensagem);
	}

	public ReCaptchaInvalidException(final Throwable causa) {
		super(causa);
	}
}
