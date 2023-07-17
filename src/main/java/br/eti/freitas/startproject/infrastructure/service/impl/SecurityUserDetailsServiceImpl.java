package br.eti.freitas.startproject.infrastructure.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.infrastructure.custom.CustomUserPrincipal;
import br.eti.freitas.startproject.infrastructure.error.ConstantError;
import br.eti.freitas.startproject.infrastructure.model.User;
import br.eti.freitas.startproject.infrastructure.repository.UserRepository;;

@Service
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		try {
			User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
			if (user == null) {
				throw new UsernameNotFoundException(String.format(ConstantError.USER_NOT_FOUND, usernameOrEmail));
			}

			return new CustomUserPrincipal(user);

		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
