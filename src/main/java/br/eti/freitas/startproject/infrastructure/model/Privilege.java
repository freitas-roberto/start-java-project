package br.eti.freitas.startproject.infrastructure.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PRIVILEGE", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "resource", "type" }) })
@SQLDelete(sql = "UPDATE PRIVILEGE SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
public class Privilege implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 40, nullable = false)
	private String resource;

	@Column(length = 40, nullable = false)
	private String type;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled;

	@JsonIgnore
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;

	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;

	@ManyToMany(mappedBy = "privileges")
	private Collection<User> users;

	public Privilege() {
	}

	public Privilege(String resource, String type, boolean enabled) {
		this.resource = resource;
		this.type = type;
		this.enabled = enabled;
		this.deleted = false;
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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		return this.resource;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Privilege [id=").append(id).append(", resource=").append(resource)
				.append(", type=").append(type).append(", enabled=").append(enabled).append(", deleted=")
				.append(deleted).append(", roles=").append(roles).append(", users=").append(users).append("]");
		return builder.toString();
	}

}
