package br.eti.freitas.startproject.infrastructure.service;

import br.eti.freitas.startproject.infrastructure.dto.LoginDto;
import br.eti.freitas.startproject.infrastructure.dto.SignUpDto;
import br.eti.freitas.startproject.infrastructure.dto.TokenDto;

public interface AuthenticateService {

	public TokenDto authenticateUser(LoginDto loginDto);
	
	public SignUpDto registerUser(SignUpDto signUpDto);
	
}
