package com.swamy.service;

import java.util.List;

import com.swamy.dto.DepartmentDto;

public interface DepartmentService {

	DepartmentDto saveDepartment(DepartmentDto departmentDto);

	DepartmentDto getDepartmentByCode(String departmentCode);

	List<DepartmentDto> getAllDepartments();

	DepartmentDto updateDepartment(String departmentCode, DepartmentDto departmentDto);

	String deleteDepartment(String departmentCode);
}
