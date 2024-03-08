package com.faraday.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * Info Visualizzabile Nell'URL http://localhost:[port]/swagger-ui.html
 */

@Configuration
@EnableSwagger2
@SwaggerDefinition(
		 info = @Info(
				 title = "micro-service-user", 
				 version = "0.0.1-SNAPSHOT",
				 description = "Gestisce le utenze di accesso ai microservizi",
				 contact = @Contact(
						name = "Paolo Acquaviva",
						email = "paoloacqua@hotmail.it"
				 ),
				 license = @License(
						name = "EULA",
						url = "https://it.wikipedia.org/wiki/EULA"
				 )
					       
		),
		consumes = {"application/json", "application/xml"},
	    produces = {"application/json", "application/xml"}
		  
)
public class SwaggerConfig
{
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
				.build();
	}
}