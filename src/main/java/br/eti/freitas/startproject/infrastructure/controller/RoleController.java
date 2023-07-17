package br.eti.freitas.startproject.infrastructure.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.startproject.infrastructure.dto.RoleDto;
import br.eti.freitas.startproject.infrastructure.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This controller is responsible for the management of the <b>Roles</b>
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Roles", description = "Endpoints for managing Roles")
@RequestMapping("/api/v1")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PreAuthorize("hasPermission('role', 'read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	@Operation(summary = "Find by role id", description = "Method used to find an Organization by role id")
	public ResponseEntity<RoleDto> getRole(@PathVariable("id") long id) {
		RoleDto roleDto = roleService.getRole(id);
		return ResponseEntity.ok().body(roleDto);
	}

	@PreAuthorize("hasPermission('role', 'read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	@Operation(summary = "Find by name", description = "Method used to find an Organization by name")
	public ResponseEntity<RoleDto> getRole(@RequestParam(name="name", required=true) String roleName) {
		RoleDto roleDto = roleService.getRole(roleName);
		return ResponseEntity.ok().body(roleDto);
	}

	@PreAuthorize("hasPermission('role', 'read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@Operation(summary = "Find all Roles", description = "Method used to find all Roles")
	public ResponseEntity<List<RoleDto>> getRoles() {
		List<RoleDto> roles = roleService.getRoles();
		return ResponseEntity.ok().body(roles);
	}

	@PreAuthorize("hasPermission('role', 'read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/roles/page", method = RequestMethod.GET)
	@Operation(summary = "Find all Roles", description = "Method used to find all Roles and show per page")
	public ResponseEntity<Page<RoleDto>> getRoles(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "20") Integer size,
			@RequestParam(value = "sort", defaultValue = "name") String sort,
			@RequestParam(value = "direction", defaultValue = "ASC") Direction direction) {
		PageRequest pageRequest = PageRequest.of(page, size, direction, sort);
		Page<RoleDto> rolesPage = roleService.getRoles(pageRequest);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(rolesPage);
	}

	@PreAuthorize("hasPermission('role', 'write') or hasRole('SYSTEM')")
	@RequestMapping(value = "/role", method = RequestMethod.POST)
	@Operation(summary = "Add a new Role", description = "Method used to add a new Role")
	public ResponseEntity<Void> writeRole(@Validated @RequestBody RoleDto roleDto) {
		RoleDto createdRole = roleService.createRole(roleDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{roleid}")
				.buildAndExpand(createdRole.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PreAuthorize("hasPermission('role', 'delete') or hasRole('SYSTEM')")
	@RequestMapping(value = "/role/{roleid}", method = RequestMethod.DELETE)
	@Operation(summary = "Delete by role id", description = "Method used to delete an Role by role id")
	public ResponseEntity<Void> deleteRole(@PathVariable("id") long id) throws Exception {
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasPermission('role:user', 'write') or hasRole('SYSTEM')")
	@RequestMapping(value = "/role/user", method = RequestMethod.POST)
	@Operation(summary = "Add a Role to the User", description = "Method used to add a new Role to the User")
	public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUserForm form) {
		roleService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
}

class RoleToUserForm {

	private String username;
	private String roleName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
