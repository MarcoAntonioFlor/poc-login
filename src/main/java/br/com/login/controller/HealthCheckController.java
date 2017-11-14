package br.com.login.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(value = "/health-check")
public class HealthCheckController {

	@Value("${project.version}")
	private String version;

	@GetMapping()
	public Status healthCheck() {
		return Status.of("OK", version);
	}

	@Data
	@AllArgsConstructor(staticName="of")
	static class Status {
		private String status;
		private String version;
	}

}
