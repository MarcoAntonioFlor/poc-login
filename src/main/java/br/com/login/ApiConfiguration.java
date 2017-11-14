package br.com.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.client.ApiClient;
import io.swagger.client.api.AutenticacaoControllerApi;
import io.swagger.client.api.DemonstrativoControllerApi;
import io.swagger.client.api.DocumentoMedicoControllerApi;
import io.swagger.client.api.EmailControllerApi;
import io.swagger.client.api.EmpresaControllerApi;
import io.swagger.client.api.EspecialidadeControllerApi;
import io.swagger.client.api.EstadoControllerApi;
import io.swagger.client.api.MarcaControllerApi;
import io.swagger.client.api.MedicoControllerApi;
import io.swagger.client.api.NotaFiscalControllerApi;
import io.swagger.client.api.PerfilControllerApi;
import io.swagger.client.api.ResumoMedicoControllerApi;
import io.swagger.client.api.TomadorControllerApi;
import io.swagger.client.api.UsuarioControllerApi;
import io.swagger.client.auth.HttpBasicAuth;

@Configuration
public class ApiConfiguration {

	private static String typeAuthentication = "basic";
	
	@Value("${higiaapi.basepath}")
	private String basepath;
	
	@Value("${higiaapi.authentication.username}")
	private String username;
	
	@Value("${higiaapi.authentication.password}")
	private String password;
	
	@Bean
	public ApiClient getApiClient() {
		final ApiClient apiClient = new ApiClient();
		apiClient.setBasePath(basepath);
		apiClient.addAuthorization(typeAuthentication, getHttpBasicAuthentication());
		apiClient.setCredentials(username, password);
		return apiClient;
	}
	
	private HttpBasicAuth getHttpBasicAuthentication(){
		return new HttpBasicAuth();	
	}
	 
	
	public <T extends ApiClient.Api> T getClientGenericApi(final Class<T> api) {
		return getApiClient().buildClient(api);
	}

	@Bean
	public AutenticacaoControllerApi getAutenticacaoControllerApi() {
		return getClientGenericApi(AutenticacaoControllerApi.class);
	}

	@Bean
	public DocumentoMedicoControllerApi getDocumentoMedicoWS() {
		return getClientGenericApi(DocumentoMedicoControllerApi.class);
	}

	@Bean
	public EmailControllerApi getEmailControllerApi() {
		return getClientGenericApi(EmailControllerApi.class);
	}

	@Bean
	public EmpresaControllerApi getEmpresaControllerApi() {
		return getClientGenericApi(EmpresaControllerApi.class);
	}

	@Bean
	public EspecialidadeControllerApi getEspecialidadeControllerApi() {
		return getClientGenericApi(EspecialidadeControllerApi.class);
	}

	@Bean
	public EstadoControllerApi getEstadoControllerApi() {
		return getClientGenericApi(EstadoControllerApi.class);
	}

	@Bean
	public MarcaControllerApi getMarcaControllerApi() {
		return getClientGenericApi(MarcaControllerApi.class);
	}

	@Bean
	public MedicoControllerApi getMedicoControllerApi() {
		return getClientGenericApi(MedicoControllerApi.class);
	}

	@Bean
	public NotaFiscalControllerApi getNotaFiscalControllerApi() {
		return getClientGenericApi(NotaFiscalControllerApi.class);
	}

	@Bean
	public PerfilControllerApi getPerfilControllerApi() {
		return getClientGenericApi(PerfilControllerApi.class);
	}

	@Bean
	public ResumoMedicoControllerApi getResumoMedicoControllerApi() {
		return getClientGenericApi(ResumoMedicoControllerApi.class);
	}

	@Bean
	public TomadorControllerApi getTomadorControllerApi() {
		return getClientGenericApi(TomadorControllerApi.class);
	}

	@Bean
	public UsuarioControllerApi getUsuarioControllerApi() {
		return getClientGenericApi(UsuarioControllerApi.class);
	}

	@Bean
	public DemonstrativoControllerApi getDemonstrativoControllerApi() {
		return getClientGenericApi(DemonstrativoControllerApi.class);
	}
}
