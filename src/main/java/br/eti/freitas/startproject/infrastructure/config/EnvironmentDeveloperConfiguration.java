package br.eti.freitas.startproject.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.expression.ParseException;

import br.eti.freitas.startproject.infrastructure.service.DataBaseService;

@Configuration
@Profile("developer")
public class EnvironmentDeveloperConfiguration {

	@Autowired
	private DataBaseService dataBaseService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean initialSetup() throws ParseException {

		if (!"create".equals(strategy)) {
			return false;
		}

		dataBaseService.uploadDeveloperDataBase();
		return true;
	}

}
