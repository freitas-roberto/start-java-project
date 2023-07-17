package br.eti.freitas.startproject.infrastructure.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This class is responsible for allowing data transfer from <b>Role</b> between subsystems
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-12
 */
public class RoleDto {

	private Long id;
	
	@NotBlank
	@Min(3)
	@Max(50)
	private String name;
	
	private String description;
	
	@NotNull
	private boolean enabled;

	public RoleDto() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		builder.append("RoleDto [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", enabled=").append(enabled).append("]");
		return builder.toString();
	}

}
