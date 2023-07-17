package br.eti.freitas.startproject.infrastructure.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.eti.freitas.startproject.infrastructure.dto.PrivilegeDto;

public interface PrivilegeService {

	public PrivilegeDto getPrivilege(Long id);

	public PrivilegeDto getPrivilege(String resource);

	public List<PrivilegeDto> getPrivileges();

	public Page<PrivilegeDto> getPrivileges(Pageable pageable);

	public PrivilegeDto createPrivilege(PrivilegeDto privilegeDto);

	public PrivilegeDto updatePrivilege(PrivilegeDto privilegeDto);

	public void deletePrivilege(Long Id);

	public void addPrivilegeToRole(String roleName, String resource, String type);

}
