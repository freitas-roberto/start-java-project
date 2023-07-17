package br.eti.freitas.startproject.infrastructure.service;

import java.util.List;

import br.eti.freitas.startproject.infrastructure.dto.UserDto;

public interface UserService {

	public List<UserDto> getUsers();

	public UserDto getById(Long id);

	public UserDto getUser(String username);

	public UserDto createUser(UserDto userDto);

	public UserDto updateUser(UserDto userDto);

	public void deleteUser(Long id);

	public void addPrivilegeToUser(String username, String resource, String type);

}
