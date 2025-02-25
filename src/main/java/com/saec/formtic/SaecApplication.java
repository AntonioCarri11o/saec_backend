package com.saec.formtic;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
@Slf4j
public class SaecApplication {

	private static final Logger log = LoggerFactory.getLogger(SaecApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SaecApplication.class, args);
		openSwagger();
	}

	@Bean
	public OpenAPI customApi() {
		return new OpenAPI().info(new Info().title("SAEC Apis").version("1.0").description("APIS to SAEC service")
				.termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public RestTemplate restTemplate() {
		return  new RestTemplate();
	}

	private static void openSwagger() {
		try {
			URI swaggerUri = new URI("http://localhost:8080/doc/swagger-ui/index.html");

			if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(swaggerUri);
			} else {
                log.info("{}", swaggerUri);
			}
		} catch (Exception e) {
            log.error("error {}", e.getMessage());
		}
	}

}
