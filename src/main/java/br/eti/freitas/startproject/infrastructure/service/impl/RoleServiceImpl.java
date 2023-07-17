package br.eti.freitas.startproject.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.infrastructure.dto.RoleDto;
import br.eti.freitas.startproject.infrastructure.exception.EntityNotFoundException;
import br.eti.freitas.startproject.infrastructure.mapper.RoleMapper;
import br.eti.freitas.startproject.infrastructure.model.Role;
import br.eti.freitas.startproject.infrastructure.model.User;
import br.eti.freitas.startproject.infrastructure.repository.RoleRepository;
import br.eti.freitas.startproject.infrastructure.repository.UserRepository;
import br.eti.freitas.startproject.infrastructure.service.RoleService;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<RoleDto> getRoles() {
		List<Role> roles = roleRepository.findAll();
		return roles.stream().map((role) -> RoleMapper.MAPPER.mapToDto(role)).collect(Collectors.toList());
	}

	@Override
	public Page<RoleDto> getRoles(Pageable pageable) {
		Page<Role> roles = roleRepository.findAll(pageable);
		List<RoleDto> rolesPage = RoleMapper.MAPPER.mapToDto(roles.getContent());
 		Page<RoleDto> rolesPageDto = new PageImpl<>(rolesPage, pageable, roles.getTotalElements());
		return rolesPageDto;
	}

	@Override
	public RoleDto getRole(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
		return RoleMapper.MAPPER.mapToDto(role);
	}

	@Override
	public RoleDto getRole(String roleName) {
		Role role = roleRepository.findByName(roleName)
				.orElseThrow(() -> new EntityNotFoundException(User.class, "name", roleName));
		return RoleMapper.MAPPER.mapToDto(role);
	}

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = RoleMapper.MAPPER.mapToModel(roleDto);
		Role createdRole = roleRepository.save(role);
		RoleDto createdRoleDto = RoleMapper.MAPPER.mapToDto(createdRole);
		return createdRoleDto;
	}

	@Override
	public void deleteRole(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Role.class, "id", id.toString()));
		roleRepository.delete(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByUsername(username).get();
		Role role = roleRepository.findByName(roleName).get();
		user.getRoles().add(role);
	}

}
