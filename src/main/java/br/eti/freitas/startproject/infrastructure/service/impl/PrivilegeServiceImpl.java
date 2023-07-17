package br.eti.freitas.startproject.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;
import br.eti.freitas.startproject.infrastructure.exception.EntityNotFoundException;
import br.eti.freitas.startproject.infrastructure.mapper.PrivilegeMapper;
import br.eti.freitas.startproject.infrastructure.model.Privilege;
import br.eti.freitas.startproject.infrastructure.model.Role;
import br.eti.freitas.startproject.infrastructure.repository.PrivilegeRepository;
import br.eti.freitas.startproject.infrastructure.repository.RoleRepository;
import br.eti.freitas.startproject.infrastructure.service.PrivilegeService;

@Transactional
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public PrivilegeDto getPrivilege(Long id) {
		Privilege privilege = privilegeRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(Privilege.class, "id", id.toString()));
		return PrivilegeMapper.MAPPER.mapToDto(privilege);
	}

	@Override
	public PrivilegeDto getPrivilege(String resource) {
		Privilege privilege = privilegeRepository.findByResource(resource).orElseThrow(
				() -> new EntityNotFoundException(Privilege.class, "resource", resource));
		return PrivilegeMapper.MAPPER.mapToDto(privilege);
	}

	@Override
	public List<PrivilegeDto> getPrivileges() {
		List<Privilege> privileges = privilegeRepository.findAll();
		return privileges.stream().map((privilege) -> PrivilegeMapper.MAPPER.mapToDto(privilege))
				.collect(Collectors.toList());
	}

	@Override
	public Page<PrivilegeDto> getPrivileges(Pageable pageable) {
		Page<Privilege> privileges = privilegeRepository.findAll(pageable);
		List<PrivilegeDto> privilegesDto = PrivilegeMapper.MAPPER.mapToDto(privileges.getContent());
		Page<PrivilegeDto> privilegesDtoPage = new PageImpl<>(privilegesDto, pageable, privileges.getTotalElements());
		return privilegesDtoPage;
	}

	@Override
	public PrivilegeDto createPrivilege(PrivilegeDto privilegeDto) {
		Privilege privilege = PrivilegeMapper.MAPPER.mapToModel(privilegeDto);
		Privilege createdPrivilege = privilegeRepository.save(privilege);
		PrivilegeDto createdPrivilegeDto = PrivilegeMapper.MAPPER.mapToDto(createdPrivilege);
		return createdPrivilegeDto;
	}

	@Override
	public PrivilegeDto updatePrivilege(PrivilegeDto privilegeDto) {
		Privilege privilege = privilegeRepository.findById(privilegeDto.getId())
				.orElseThrow(() -> new EntityNotFoundException(Privilege.class, "id",
						privilegeDto.getId().toString()));
		privilege.setResource(privilegeDto.getResource());
		privilege.setType(privilegeDto.getType());
		privilege.setEnabled(privilegeDto.isEnabled());
		return PrivilegeMapper.MAPPER.mapToDto(privilegeRepository.save(privilege));
	}

	@Override
	public void deletePrivilege(Long id) {
		Privilege privlege = privilegeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Privilege.class, "id", id.toString()));
		privilegeRepository.delete(privlege);
	}

	@Override
	public void addPrivilegeToRole(String roleName, String resource, String type) {
		Role role = roleRepository.findByName(roleName).get();
		Privilege privilege = privilegeRepository.findByResourceAndType(resource, type).get();
		role.getPrivileges().add(privilege);
	}

}
