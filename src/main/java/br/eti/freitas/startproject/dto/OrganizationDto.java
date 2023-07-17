package br.eti.freitas.startproject.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "An Organization", title = "Organization")
public class OrganizationDto {

	private Long id;

	private UUID organizationKey;

	@Schema(description = "Organization name", example = "UsinaTECH Soluções em Tecnologia")
	@NotBlank
	private String name;

	@Schema(description = "Email address", example = "contato@usinatech.com.br")
	@NotBlank
	private String email;

	@Schema(description = "Status", example = "true or false")
	private boolean enabled;

	public OrganizationDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getOrganizationKey() {
		return organizationKey;
	}

	public void setOrganizationKey(UUID organizationKey) {
		this.organizationKey = organizationKey;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrganizationDto [id=").append(id).append(", organizationKey=")
				.append(organizationKey).append(", name=").append(name).append(", email=").append(email)
				.append(", enabled=").append(enabled).append("]");
		return builder.toString();
	}

}
