package com.swamy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.swamy.dto.DepartmentDto;

@FeignClient(name = "DEPARTMENT-SERVICE")
public interface DepartmentClient {

	@GetMapping("/api/departments/{dept-code}")
	public DepartmentDto getDepartmentByCode(@PathVariable("dept-code") String departmentCode);
}
