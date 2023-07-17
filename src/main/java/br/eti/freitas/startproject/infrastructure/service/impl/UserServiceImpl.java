package br.eti.freitas.startproject.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.infrastructure.dto.UserDto;
import br.eti.freitas.startproject.infrastructure.exception.EntityNotFoundException;
import br.eti.freitas.startproject.infrastructure.mapper.UserMapper;
import br.eti.freitas.startproject.infrastructure.model.Privilege;
import br.eti.freitas.startproject.infrastructure.model.User;
import br.eti.freitas.startproject.infrastructure.repository.PrivilegeRepository;
import br.eti.freitas.startproject.infrastructure.repository.UserRepository;
import br.eti.freitas.startproject.infrastructure.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserDto> getUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((User) -> UserMapper.MAPPER.mapToDto(User)).collect(Collectors.toList());
	}

	@Override
	public UserDto getById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
		return UserMapper.MAPPER.mapToDto(user);
	}

	@Override
	public UserDto getUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(User.class, "username", username));
		return UserMapper.MAPPER.mapToDto(user);

	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = UserMapper.MAPPER.mapToModel(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User createdUser = userRepository.save(user);
		UserDto createdUserDto = UserMapper.MAPPER.mapToDto(createdUser);
		return createdUserDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto) {
		User user = userRepository.findById(userDto.getId()).orElseThrow(
				() -> new EntityNotFoundException(User.class, "userId", userDto.getId().toString()));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return UserMapper.MAPPER.mapToDto(userRepository.save(user));
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(User.class, "userId", id.toString()));
		userRepository.delete(user);
	}

	@Override
	public void addPrivilegeToUser(String username, String resource, String type) {
		User user = userRepository.findByUsername(username).get();
		Privilege privilege = privilegeRepository.findByResourceAndType(resource, type).get();
		user.getPrivileges().add(privilege);
	}

}
