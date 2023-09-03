package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.ORGANIZATION_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.ORGANIZATION_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.swamy.dto.OrganizationDto;
import com.swamy.entity.Organization;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.OrganizationRepository;
import com.swamy.service.OrganizationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationRepository organizationRepository;

	private ModelMapper modelMapper;

	private AppProperties appProperties;

	@Override
	public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

		Organization organization = null;

		organization = modelMapper.map(organizationDto, Organization.class);

		organization = organizationRepository.save(organization);

		return modelMapper.map(organization, OrganizationDto.class);
	}

	@Override
	public OrganizationDto getOrganizationByCode(String organizationCode) {

		Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(ORGANIZATION_NOT_FOUND), organizationCode)));

		return modelMapper.map(organization, OrganizationDto.class);
	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {

		List<Organization> organizations = organizationRepository.findAll();

		return organizations.stream().map(organization -> modelMapper.map(organization, OrganizationDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public OrganizationDto updateOrganization(String organizationCode, OrganizationDto organizationDto) {

		Organization organization = null;

		organization = organizationRepository.findByOrganizationCode(organizationCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(ORGANIZATION_NOT_FOUND), organizationCode)));

		organization.setOrganizationName(organizationDto.getOrganizationName());
		organization.setOrganizationCode(organizationDto.getOrganizationCode());
		organization.setOrganizationDescription(organizationDto.getOrganizationDescription());

		organization = organizationRepository.save(organization);

		return modelMapper.map(organization, OrganizationDto.class);
	}

	@Override
	public String deleteOrganization(String organizationCode) {

		Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(ORGANIZATION_NOT_FOUND), organizationCode)));

		organizationRepository.deleteById(organization.getOrganizationId());

		return appProperties.getMessages().get(ORGANIZATION_DELETION_SUCCEEDED) + organizationCode;
	}
}
