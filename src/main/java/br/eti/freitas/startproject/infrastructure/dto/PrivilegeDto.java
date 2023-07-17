package br.eti.freitas.startproject.infrastructure.dto;

/**
 * This class is responsible for allowing data transfer from <b>Privilege</b> between subsystems
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-12
 */
public class PrivilegeDto {

	private Long id;
	private String resource;
	private String type;
	private boolean enabled;

	public PrivilegeDto() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		builder.append("PrivilegeDto [id=").append(id).append(", resource=").append(resource).append(", type=")
				.append(type).append(", enabled=").append(enabled).append("]");
		return builder.toString();
	}

}
