package br.eti.freitas.startproject.infrastructure.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.eti.freitas.startproject.infrastructure.error.CustomError;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		
		CustomError customRestError = new CustomError(HttpStatus.UNAUTHORIZED, ">>>>>>>>>>>>> RF TESTE >>>>>>>>>>>>>>>>>>>>>>>>>>", request.getServletPath(), ex);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), customRestError);		
	}

}
