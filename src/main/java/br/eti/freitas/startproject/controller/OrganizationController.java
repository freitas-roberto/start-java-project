package br.eti.freitas.startproject.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.startproject.dto.OrganizationDto;
import br.eti.freitas.startproject.infrastructure.dto.ErrorDto;
import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This controller is responsible for the management of the <b>Organization</b>
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Organization", description = "Endpoints for managing Organization")
@RequestMapping(value = "/api/v1")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@Operation( summary = "Get Organization by id"
			  , description = "Method used is used to get a single resource from the database"
		      )
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'read') or hasRole('SYSTEM')")
	@GetMapping(value = "/organization/{id}")
	public ResponseEntity<OrganizationDto> getOrganization(@PathVariable("id") Long id) {
		OrganizationDto organizationDto = organizationService.getOrganization(id);
		return ResponseEntity.ok().body(organizationDto);
	}

	@Operation( summary = "Get all Oganizations" 
			  , description = "Method used to get all database resources"
		      )
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'read') or hasRole('SYSTEM')")
	@GetMapping(value = "/organizations")
	public ResponseEntity<List<OrganizationDto>> getOganizations() {
		List<OrganizationDto> organizations = organizationService.getOrganizations();
		return ResponseEntity.ok().body(organizations);
	}

	@Operation( summary = "Get all Organizations"
			  , description = "Method used to get all database resources and show by pages"
		      )
	@ApiResponses({ @ApiResponse(responseCode = "206", description = "Partial Content", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'read') or hasRole('SYSTEM')")
	@GetMapping(value = "/organizations/page")
	public ResponseEntity<Page<OrganizationDto>> getOganizations(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "sort", defaultValue = "name") String sort,
			@RequestParam(value = "direction", defaultValue = "ASC") Direction direction) {
		PageRequest pageRequest = PageRequest.of(page, size, direction, sort);
		Page<OrganizationDto> organizationsPage = organizationService.getOrganizations(pageRequest);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(organizationsPage);
	}

	@Operation( summary = "Create a new Organization"
			  , description = "Method create is used to create a new resource into database"
		      )
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'write') or hasRole('SYSTEM')")
	@PostMapping(value = "/organization")
	public ResponseEntity<Void> writeOrganization(@Validated @RequestBody OrganizationDto organizationDto) {
		OrganizationDto createdOrganization = organizationService.createOrganization(organizationDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdOrganization.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation( summary = "Update an existing Organization"
			  , description = "Method update is used to change a particular resource from the database"
		      )
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'write') or hasRole('SYSTEM')")
	@PutMapping(value = "/organization/{id}")
	public ResponseEntity<Void> writeOrganization(@PathVariable("id") long id,
			@RequestBody OrganizationDto organizationDto) {
		organizationDto.setId(id);
		organizationService.updateOrganization(organizationDto);
		return ResponseEntity.noContent().build();
	}

	@Operation( summary = "Delete an existing Organization"
			  , description = "Method delete is used to remove a particular resource from the database"
		      )
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "404", description = "Not found")
			  		})
	@PreAuthorize("hasPermission('organization', 'delete') or hasRole('SYSTEM')")
	@DeleteMapping(value = "/organization/{id}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable("id") long id) {
		organizationService.deleteOrganization(id);
		return ResponseEntity.noContent().build();
	}

}
