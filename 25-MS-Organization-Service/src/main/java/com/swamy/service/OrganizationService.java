package com.swamy.service;

import java.util.List;

import com.swamy.dto.OrganizationDto;

public interface OrganizationService {

	OrganizationDto saveOrganization(OrganizationDto organizationDto);

	OrganizationDto getOrganizationByCode(String organizationCode);

	List<OrganizationDto> getAllOrganizations();

	OrganizationDto updateOrganization(String organizationCode, OrganizationDto organizationDto);

	String deleteOrganization(String organizationCode);
}
