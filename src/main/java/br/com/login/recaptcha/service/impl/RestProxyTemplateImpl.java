package br.com.login.recaptcha.service.impl;

import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.login.recaptcha.service.RestTemplateService;

@Component
@Profile({ "local", "local-pg", "default" })
public final class RestProxyTemplateImpl implements RestTemplateService {

	final Logger logger = LogManager.getLogger(RestProxyTemplateImpl.class);

	RestTemplate restTemplate = new RestTemplate();

	@Value("${proxy.host}")
	String host;

	@Value("${proxy.port}")
	String port;

	@PostConstruct
	public void init() {
		int portNr = -1;
		try {
			portNr = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			logger.error("Unable to parse the proxy port number");
		}
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(host, portNr);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		factory.setProxy(proxy);

		restTemplate.setRequestFactory(factory);
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
