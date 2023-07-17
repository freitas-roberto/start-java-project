package br.eti.freitas.startproject.infrastructure.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.startproject.infrastructure.dto.ErrorDto;
import br.eti.freitas.startproject.infrastructure.dto.LoginDto;
import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.dto.SignUpDto;
import br.eti.freitas.startproject.infrastructure.dto.TokenDto;
import br.eti.freitas.startproject.infrastructure.service.impl.AuthenticateServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This controller is responsible for the management of the <b>Authenticate</b>
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
@RestController
@SecurityRequirements(value = {})
@Tag(name = "Authenticate", description = "Endpoints for authenticate users")
@RequestMapping("/api/auth")
public class AuthenticateController {

	@Autowired
	private AuthenticateServiceImpl authenticateService;

	@Operation(summary = "Logs user into the system", 
			   description = "Method used to authenticate user on platform",
			   security = {})
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
		@ApiResponse(responseCode = "404", description = "Not found")
  		})
	@PostMapping(value = "/signin")
	public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginDto loginDto) throws Exception {
		TokenDto tokenDto = authenticateService.authenticateUser(loginDto);
		return ResponseEntity.ok().body(tokenDto);
	}

	@Operation(summary = "Create user", description = "Method used to create a new user on the platform",
				security = {})
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PrivilegeDto.class))),
		@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
		@ApiResponse(responseCode = "404", description = "Not found")
  		})
	@PostMapping(value = "/signup")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpDto signUpDto) {
		SignUpDto signUp = authenticateService.registerUser(signUpDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
				.buildAndExpand(signUp.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	}

}
