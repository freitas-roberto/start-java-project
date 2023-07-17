package br.eti.freitas.startproject.infrastructure.dto;

/**
 * This class is responsible for allowing data transfer from <b>Login</b>
 * between subsystems
 *
 * @author Roberto Freitas
 * @version 1.0
 * @since 2023-03-12
 */
public class LoginDto {

	private String username;
	private String password;

	public LoginDto() {
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
		builder.append("LoginDto [username=").append(username).append(", password=").append(password).append("]");
		return builder.toString();
	}

}
