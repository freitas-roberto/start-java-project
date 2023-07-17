package br.eti.freitas.startproject.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.service.PrivilegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This controller is responsible for the management of the <b>Privilege</b>
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Privileges", description = "Endpoints for managing Privileges")
@RequestMapping(value = "/api/v1")
public class PrivilegeController {

	@Autowired
	private PrivilegeService privilegeService;

	@Operation( summary = "Get Privilege by id"
			  , description = "Method used is used to get a single resource from the database"
			  , responses = {
						        @ApiResponse(responseCode = "200", description = "OK"),
						        @ApiResponse(responseCode = "403", description = "Forbidden"),
						        @ApiResponse(responseCode = "404", description = "Not found")

			  })
	@PreAuthorize("hasPermission('privilege','read') or hasRole('SYSTEM')")
	@GetMapping(value = "/privilege/{id}")
	public ResponseEntity<PrivilegeDto> getPrivilege(@PathVariable("id") Long id) {
		PrivilegeDto privilegeDto = privilegeService.getPrivilege(id);
		return ResponseEntity.ok().body(privilegeDto);
	}

	@Operation(summary = "Find Privilegeby privilege name", description = "Method used to find an Privilege by privilege name")

	@PreAuthorize("hasPermission('privilege','read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/privilege/", method = RequestMethod.GET)
	public ResponseEntity<PrivilegeDto> getPrivilege(@RequestParam(name = "name", required = true) String privilegeName) {
		PrivilegeDto privilegeDto = privilegeService.getPrivilege(privilegeName);
		return ResponseEntity.ok().body(privilegeDto);
	}

	@PreAuthorize("hasPermission('privilege','read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/privileges", method = RequestMethod.GET)
	@Operation(summary = "Find all Privileges", description = "Method used to find all Privileges")
	public ResponseEntity<List<PrivilegeDto>> getPrivileges() {
		List<PrivilegeDto> privileges = privilegeService.getPrivileges();
		return ResponseEntity.ok().body(privileges);
	}

	@PreAuthorize("hasPermission('privilege','read') or hasRole('SYSTEM')")
	@RequestMapping(value = "/privileges/page", method = RequestMethod.GET)
	@Operation(summary = "Find all Privileges paginated", description = "Method used to find all Privileges and show per page")
	@ApiResponses(@ApiResponse(responseCode = "206", description = "Partial Content", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrivilegeDto.class))))
	public ResponseEntity<Page<PrivilegeDto>> getPrivileges(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "20") Integer size,
			@RequestParam(value = "sort", defaultValue = "name") String sort,
			@RequestParam(value = "direction", defaultValue = "ASC") Direction direction) {
		PageRequest pageRequest = PageRequest.of(page, size, direction, sort);
		Page<PrivilegeDto> privilegesPage = privilegeService.getPrivileges(pageRequest);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(privilegesPage);
	}

	@PreAuthorize("hasPermission('privilege', 'write') ")
	@PutMapping(value = "/privilege/{id}")
	@Operation(summary = "Update an existing Privilege", description = "Method used to update an existing Privilege")
	public ResponseEntity<Void> writeOrganization(@PathVariable("id") long id,
			@RequestBody PrivilegeDto privilegeDto) {
		privilegeDto.setId(id);
		privilegeService.updatePrivilege(privilegeDto);
		return ResponseEntity.noContent().build();
	}

}
