package br.eti.freitas.startproject.infrastructure.dto;

/**
 * This class is responsible for allowing data transfer from <b>Token</b> between subsystems
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-01
 */
public class TokenDto {

	private String token;
	private String type;

	public TokenDto() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TokenDto [token=").append(token).append(", type=").append(type).append("]");
		return builder.toString();
	}

}
