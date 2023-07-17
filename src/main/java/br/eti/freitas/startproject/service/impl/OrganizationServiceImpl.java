package br.eti.freitas.startproject.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.eti.freitas.startproject.dto.OrganizationDto;
import br.eti.freitas.startproject.infrastructure.exception.EntityNotFoundException;
import br.eti.freitas.startproject.mapper.OrganizationMapper;
import br.eti.freitas.startproject.model.Organization;
import br.eti.freitas.startproject.repository.OrganizationRepository;
import br.eti.freitas.startproject.service.OrganizationService;

@Transactional
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public OrganizationDto getOrganization(Long id) {
		Organization organization = organizationRepository.findById(id).orElseThrow(
				() -> new  EntityNotFoundException(Organization.class, "id", id.toString()));
		return OrganizationMapper.MAPPER.mapToDto(organization);
	}

	@Override
	public List<OrganizationDto> getOrganizations() {
		List<Organization> organizations = organizationRepository.findAll();
		return organizations.stream().map((organization) -> OrganizationMapper.MAPPER.mapToDto(organization))
				.collect(Collectors.toList());
	}

	@Override
	public Page<OrganizationDto> getOrganizations(Pageable pageable) {
		Page<Organization> organizationsPage = organizationRepository.findAll(pageable);
		List<OrganizationDto> organizationsDto = OrganizationMapper.MAPPER.mapToDto(organizationsPage.getContent());
		Page<OrganizationDto> organizationsDtoPage = new PageImpl<>(organizationsDto, pageable, organizationsPage.getTotalElements());
		return organizationsDtoPage;
	}

	@Override
	public OrganizationDto createOrganization(OrganizationDto organizationDto) {
		Organization organization = OrganizationMapper.MAPPER.mapToModel(organizationDto);
		Organization createdOrganization = organizationRepository.save(organization);
		OrganizationDto createdOrganizationDto = OrganizationMapper.MAPPER.mapToDto(createdOrganization);
		return createdOrganizationDto;
	}

	@Override
	public OrganizationDto updateOrganization(OrganizationDto organizationDto) {
		Organization organization = organizationRepository.findById(organizationDto.getId())
				.orElseThrow(() -> new EntityNotFoundException(Organization.class, "id",
						organizationDto.getId().toString()));
		organization.setName(organizationDto.getName());
		organization.setEmail(organizationDto.getEmail());
		organization.setEnabled(organizationDto.isEnabled());
		return OrganizationMapper.MAPPER.mapToDto(organizationRepository.save(organization));
	}

	@Override
	public void deleteOrganization(Long id) {
		Organization organization = organizationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Organization.class, "id", id.toString()));
		organizationRepository.delete(organization);
	}

}
