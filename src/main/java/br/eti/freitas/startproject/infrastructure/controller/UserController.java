package br.eti.freitas.startproject.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.startproject.infrastructure.dto.UserDto;
import br.eti.freitas.startproject.infrastructure.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This controller is responsible for the management of the <b>Users</b>
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "Endpoints for managing Users")
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/user/{id}")
	@Operation(summary = "Find by user id", description = "Method used to find an Privilege by user id")
	@PreAuthorize("hasPermission('user', 'read') or hasRole('SYSTEM')")
	public ResponseEntity<Object> getUserById(@PathVariable(name = "id") long id) {
		UserDto userDto = userService.getById(id);
		return ResponseEntity.ok().body(userDto);
	}

	@GetMapping(value = "/user")
	@Operation(summary = "Find by username", description = "Method used to find an Privilege by username")
	@PreAuthorize("hasPermission('user', 'read') or hasRole('SYSTEM')")
	public ResponseEntity<Object> getUserById(@PathVariable(name = "username") String username) {
		UserDto userDto = userService.getUser(username);
		return ResponseEntity.ok().body(userDto);
	}

	@GetMapping(value = "/users")
	@Operation(summary = "Find all Users", description = "Method used to find all Users")
	@PreAuthorize("hasPermission('user', 'read') or hasRole('SYSTEM')")
	public ResponseEntity<List<UserDto>> readUsers() {
		List<UserDto> users = userService.getUsers();
		return ResponseEntity.ok().body(users);
	}

	@PostMapping(value = "/user")
	@Operation(summary = "Add a new User", description = "Method used to add a new User")
	@PreAuthorize("hasPermission('user', 'write') or hasRole('SYSTEM')")
	public ResponseEntity<Object> writeUser(@Validated @RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(value = "/user/{id}")
	@Operation(summary = "Update an existing User", description = "Method used to update an existing User")
	@PreAuthorize("hasPermission('user', 'write') or hasRole('SYSTEM')")
	public ResponseEntity<Object> writeUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
		userDto.setId(id);
		userService.updateUser(userDto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/user/{id}")
	@Operation(summary = "Delete User by organization user id", description = "Method used to delete an User by user id")
	@PreAuthorize("hasPermission('user', 'delete') or hasRole('SYSTEM')")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}
