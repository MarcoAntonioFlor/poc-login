package br.com.login.recaptcha.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import br.com.login.recaptcha.CaptchaSettings;
import br.com.login.recaptcha.service.ReCaptchaAttemptService;

@Service
public class ReCaptchaAttemptServiceImpl implements ReCaptchaAttemptService {

	private LoadingCache<String, Integer> attemptsCache;

	@Autowired
	private CaptchaSettings captchaSettings;

	public ReCaptchaAttemptServiceImpl() {
		super();
		attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String key) {
						return 0;
					}
				});
	}

	public void reCaptchaSucceeded(String key) {
		attemptsCache.invalidate(key);
	}

	public void reCaptchaFailed(String key) {
		int attempts = attemptsCache.getUnchecked(key);
		attempts++;
		attemptsCache.put(key, attempts);
	}

	public boolean isBlocked(String key) {
		return attemptsCache.getUnchecked(key) >= captchaSettings.getMaxAttempt();
	}
}
