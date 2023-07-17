package br.eti.freitas.startproject.infrastructure.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.eti.freitas.startproject.infrastructure.dto.RoleDto;

public interface RoleService {

	public List<RoleDto> getRoles();

	public Page<RoleDto> getRoles(Pageable pageable);

	public RoleDto getRole(Long id);

	public RoleDto getRole(String roleName);

	public RoleDto createRole(RoleDto roleDto);

	public void deleteRole(Long Id);

	public void addRoleToUser(String username, String roleName);

}
