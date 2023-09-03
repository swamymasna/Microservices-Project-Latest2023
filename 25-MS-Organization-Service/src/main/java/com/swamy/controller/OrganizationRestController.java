package com.swamy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.OrganizationDto;
import com.swamy.service.OrganizationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/organizations")
@AllArgsConstructor
public class OrganizationRestController {

	private OrganizationService organizationService;

	@PostMapping
	public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto OrganizationDto) {
		return new ResponseEntity<>(organizationService.saveOrganization(OrganizationDto), HttpStatus.CREATED);
	}

	@GetMapping("/{org-code}")
	public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable("org-code") String organizationCode) {
		return new ResponseEntity<>(organizationService.getOrganizationByCode(organizationCode), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
		return new ResponseEntity<>(organizationService.getAllOrganizations(), HttpStatus.OK);
	}

	@PutMapping("/{org-code}")
	public ResponseEntity<OrganizationDto> updateOrganization(@PathVariable("org-code") String organizationCode,
			OrganizationDto organizationDto) {
		return new ResponseEntity<>(organizationService.updateOrganization(organizationCode, organizationDto),
				HttpStatus.OK);
	}

	@DeleteMapping("/{org-code}")
	public ResponseEntity<String> deleteOrganization(@PathVariable("org-code") String organizationCode) {
		return new ResponseEntity<>(organizationService.deleteOrganization(organizationCode), HttpStatus.OK);
	}
}
