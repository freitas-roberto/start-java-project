package br.eti.freitas.startproject.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
/**
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@Configuration
public class ApplicationConfiguration {

	public ModelMapper modelMapper(){
		 ModelMapper modelMapper = new ModelMapper();
		 return modelMapper;
	}
	
}
