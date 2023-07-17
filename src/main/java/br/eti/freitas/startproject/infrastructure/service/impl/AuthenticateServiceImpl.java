package br.eti.freitas.startproject.infrastructure.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.infrastructure.custom.CustomUserPrincipal;
import br.eti.freitas.startproject.infrastructure.dto.LoginDto;
import br.eti.freitas.startproject.infrastructure.dto.SignUpDto;
import br.eti.freitas.startproject.infrastructure.dto.TokenDto;
import br.eti.freitas.startproject.infrastructure.error.ConstantError;
import br.eti.freitas.startproject.infrastructure.exception.EntityNotFoundException;
import br.eti.freitas.startproject.infrastructure.mapper.AuthenticateMapper;
import br.eti.freitas.startproject.infrastructure.model.User;
import br.eti.freitas.startproject.infrastructure.repository.UserRepository;
import br.eti.freitas.startproject.infrastructure.service.AuthenticateService;
import br.eti.freitas.startproject.infrastructure.util.JwtUtil;

@Transactional
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public TokenDto authenticateUser(LoginDto loginDto) {

		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (DisabledException ex) {
			throw new EntityNotFoundException(User.class,ConstantError.USER_DISABLED, ex.getMessage());

		} catch (BadCredentialsException ex) {
				throw new EntityNotFoundException(User.class,ConstantError.INVALID_CREDENTIALS, ex.getMessage());
		}

		UserDetails userDetails = loadUserByUsername(loginDto.getUsername());

		String token = jwtTokenService.generateToken(userDetails);

		TokenDto tokenDto = new TokenDto();
		
		tokenDto.setType("Bearer");
		tokenDto.setToken(token);

		return tokenDto;
		}

	@Override
	public SignUpDto registerUser(SignUpDto signUpDto) {

	//	User userCheck = userRepository.findByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail()).get();

		signUpDto.setUsername(signUpDto.getUsername().toLowerCase());
		signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		User user = AuthenticateMapper.MAPPER.mapToModel(signUpDto);
		User createtUser = userRepository.save(user);
		SignUpDto createdUserDto = AuthenticateMapper.MAPPER.mapToDto(createtUser);
		return createdUserDto;
	}

	private UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		try {
			User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
			if (user == null) {
//				throw new UsernameNotFoundException(
//						String.format(ConstantError.USER_NOT_FOUND, usernameOrEmail));
				throw new EntityNotFoundException(User.class,"Username or Email", usernameOrEmail);
			}
					
			return new CustomUserPrincipal(user);

		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}


}
