package br.eti.freitas.startproject.infrastructure.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.eti.freitas.startproject.infrastructure.constant.ApplicationConstant;
import br.eti.freitas.startproject.infrastructure.constant.SecurityConstant;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = "bearerToken")})
@SecuritySchemes({
	@SecurityScheme(
        name = "bearerAuth",
   		description = "Provide the JWT token. JWT token can be obtained from the Login API. For testing, use the credentials <strong>sys/1234</strong>",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
        )
})
public class OpenApiConfiguration {
	
	/**
	 * Authenticate API - Endpoint used to authenticate the user in the application and infraestructure API.
	 * @return - Endpoint definitions to authenticate API
	 * 
	 */	
    @Bean
    public GroupedOpenApi authenticateApi() {
		final String[] pathsToMatch = {SecurityConstant.SECURITY_JWT_URI_AUTHENTICATE}; 
    	final String[] packagesToScan = {SecurityConstant.SCURIRTY_JWT_PACKAGE_AUTHENTICATE};
		final String[] packagesToExclude = {SecurityConstant.SCURIRTY_JWT_PACKAGE_APPLICATION};
        return GroupedOpenApi
                .builder()
                .group("Authenticate")
                .pathsToMatch(pathsToMatch)
                .packagesToScan(packagesToScan)
				.packagesToExclude(packagesToExclude)
                .addOpenApiCustomiser(statusApiCostumizer())
                .build();
    }
	
	/**
	 * Authenticate API - Endpoint used to authenticate to consume application services API.
	 * @return - Endpoint definitions to application API
	 * 
	 */	
    @Bean
    public GroupedOpenApi applicationApi() {
		final String[] pathsToMatch = {SecurityConstant.SECURITY_JWT_URI_APPLICATION}; 
		final String[] packagesToScan = {SecurityConstant.SCURIRTY_JWT_PACKAGE_APPLICATION, "br.eti.freitas.startproject.dto"};
		final String[] packagesToExclude = {SecurityConstant.SCURIRTY_JWT_PACKAGE_AUTHENTICATE};
        return GroupedOpenApi
                .builder()
                .group("Application")
                .pathsToMatch(pathsToMatch)
                .packagesToScan(packagesToScan)
				.packagesToExclude(packagesToExclude)
                .addOpenApiCustomiser(statusApiCostumizer())
                .build();
    }    
    
	/**
	 * Application API - Endpoint used to authenticate to consume infraestructure services API.
	 * @return - Endpoint definitions to application API
	 * 
	 */	
    @Bean
    public GroupedOpenApi infrastructureApi() {
		final String[] pathsToMatch = {SecurityConstant.SECURITY_JWT_URI_APPLICATION}; 
		final String[] packagesToScan = {SecurityConstant.SCURIRTY_JWT_PACKAGE_AUTHENTICATE, "br.eti.freitas.startproject.infrastructure.dto"};
		final String[] packagesToExclude = {SecurityConstant.SCURIRTY_JWT_PACKAGE_APPLICATION};
        return GroupedOpenApi
                .builder()
                .group("Infrastructure")
                .pathsToMatch(pathsToMatch)
                .packagesToScan(packagesToScan)
				.packagesToExclude(packagesToExclude)
                .addOpenApiCustomiser(statusApiCostumizer())
                .build();
    }    
    
    private OpenApiCustomiser statusApiCostumizer() {
        return openAPI -> openAPI
            .info(apiInfo());
    }
    
	/**
	 * Add field x-tenant in header
	 * @return
	 */
	@Bean
	public OperationCustomizer customizeTenant() {
	    return (operation, handlerMethod) -> operation.addParametersItem(
	            new Parameter()
	                    .in("header")
	                    .required(true)
	                    .description("Organization Key identification")
	                    .name("X-TenantId"));
	}


    /**
     * API description
     * @return 
     */
	private Info apiInfo() {
		return new Info()
				.title(ApplicationConstant.APPLICATION_TITLE)
				.description(ApplicationConstant.APPLICATION_DESCRIPTION)
				.version(ApplicationConstant.APPLICATION_VERSION)
				.contact(apiContact())
				.license(apiLicence());
	}

	
    /**
     * License information
     * @return
     */
	private License apiLicence() {
		return new License()
				.name("MIT Licence")
				.url("https://opensource.org/licenses/mit-license.php");
	}

	/**
	 * Contact information
	 * @return
	 */
	private Contact apiContact() {
		return new Contact()
				.name("Roberto Freitas")
				.email("roberto@freitas.eti.br")
				.url("https://www.freitas.eti.br");
	}
	
}
