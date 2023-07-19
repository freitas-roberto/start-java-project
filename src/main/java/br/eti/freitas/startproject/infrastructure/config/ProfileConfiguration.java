package br.eti.freitas.startproject.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;

import br.eti.freitas.startproject.infrastructure.service.DatabaseService;

/**
 * Configuration for <b>Developer</b> environment
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@Configuration
public class ProfileConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileConfiguration.class);
	
	@Autowired
	private DatabaseService databaseService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Value("${spring.profiles.active}")
	private String profile;
	
	@Bean
	public void initialSetup() throws ParseException {

		LOG.info(">>> ACTIVE PROFILE {} WITH STRATEGY {} <<<", profile, strategy);

		if ("create".equalsIgnoreCase(strategy) || "create-drop".equalsIgnoreCase(strategy)) {
			if ("dev".equalsIgnoreCase(profile)) {
				databaseService.uploadOthersDatabases();		
			} else if ("test".equalsIgnoreCase(profile)) {
				databaseService.uploadOthersDatabases();		
			} else if ("prod".equalsIgnoreCase(profile)) {
				databaseService.uploadProductionDatabase();						
			} else {
				LOG.info("Environment does not especified.");
			}
		} else {
			LOG.info("Strategy does not especified.");			
		}
	}
	
}
