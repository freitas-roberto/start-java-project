package br.eti.freitas.startproject.infrastructure.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ROLE", schema = "security")
@SQLDelete(sql="UPDATE ROLE SET deleted=true WHERE id=?")
@Where(clause="deleted=false")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 40, nullable = false, unique = true)
	private String name;

	@Column(length = 100)
	private String description;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled;

	@JsonIgnore
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLES_PRIVILEGES", schema = "security",
				 joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
				   inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")})
	private Collection<Privilege> privileges = new ArrayList<>();

	public Role() {
	}

	public Role(String name, String description, boolean enabled) {
		this.name = name;
		this.description = description;
		this.enabled = enabled;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [id=").append(id).append(", name=").append(name).append(", enabled=")
				.append(enabled).append(", deleted=").append(deleted).append(", users=").append(users)
				.append(", privileges=").append(privileges).append("]");
		return builder.toString();
	}

}
