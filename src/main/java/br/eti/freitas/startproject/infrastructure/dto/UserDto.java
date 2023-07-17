package br.eti.freitas.startproject.infrastructure.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class is responsible for allowing data transfer from <b>User</b> between subsystems
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
public class UserDto {

	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String name;

	@Email
	@Size(min = 3, max = 100)
	private String email;

	@Size(min = 5, max = 40)
	private String username;
	
	@NotNull @NotBlank
	private String password;

	public UserDto() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [id=").append(id).append(", name=").append(name).append(", email=").append(email)
				.append(", username=").append(username).append(", password=").append(password).append("]");
		return builder.toString();
	}

}
