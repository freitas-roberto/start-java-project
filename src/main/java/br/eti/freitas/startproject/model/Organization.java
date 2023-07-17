package br.eti.freitas.startproject.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ORGANIZATION")
@SQLDelete(sql="UPDATE ORGANIZATION SET deleted=true WHERE id=?")
@Where(clause="deleted=false")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, unique = true)
	private UUID organizationKey;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(length = 100, nullable = false)
	private String email;

	@Column(columnDefinition = "boolean default true")
	private boolean enabled;

	@JsonIgnore
	@Column(columnDefinition = "boolean default false")
	private boolean deleted;

	public Organization() {
	}

	public Organization(String name, String email, boolean enabled) {
		this.name = name;
		this.email = email;
		this.enabled = enabled;
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

	public void setOrganizationKey(UUID oraganizationKey) {
		this.organizationKey = oraganizationKey;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Organization [id=").append(id).append(", organizationKey=").append(organizationKey)
				.append(", name=").append(name).append(", email=").append(email).append(", enabled=").append(enabled)
				.append(", deleted=").append(deleted).append("]");
		return builder.toString();
	}

}
