package br.eti.freitas.startproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.eti.freitas.startproject.dto.OrganizationDto;

public interface OrganizationService {

	public OrganizationDto getOrganization(Long id);

	public List<OrganizationDto> getOrganizations();

	public Page<OrganizationDto> getOrganizations(Pageable pageable);

	public OrganizationDto createOrganization(OrganizationDto organizationDto);

	public OrganizationDto updateOrganization(OrganizationDto organizationDto);

	public void deleteOrganization(Long id);

}
