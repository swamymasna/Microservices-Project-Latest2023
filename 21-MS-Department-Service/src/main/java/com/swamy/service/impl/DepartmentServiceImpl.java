package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.DEPARTMENT_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.DEPARTMENT_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.swamy.dto.DepartmentDto;
import com.swamy.entity.Department;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.DepartmentRepository;
import com.swamy.service.DepartmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentRepository departmentRepository;

	private ModelMapper modelMapper;

	private AppProperties appProperties;

	@Override
	public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

		Department department = null;

		department = modelMapper.map(departmentDto, Department.class);

		department = departmentRepository.save(department);

		return modelMapper.map(department, DepartmentDto.class);
	}

	@Override
	public DepartmentDto getDepartmentByCode(String departmentCode) {

		Department department = departmentRepository.findByDepartmentCode(departmentCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(DEPARTMENT_NOT_FOUND), departmentCode)));
		return modelMapper.map(department, DepartmentDto.class);
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {

		List<Department> departments = departmentRepository.findAll();

		return departments.stream().map(department -> modelMapper.map(department, DepartmentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public DepartmentDto updateDepartment(String departmentCode, DepartmentDto departmentDto) {

		Department department = null;

		department = departmentRepository.findByDepartmentCode(departmentCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(DEPARTMENT_NOT_FOUND), departmentCode)));

		department.setDepartmentName(departmentDto.getDepartmentName());
		department.setDepartmentCode(departmentDto.getDepartmentCode());
		department.setDepartmentDescription(departmentDto.getDepartmentDescription());

		department = departmentRepository.save(department);

		return modelMapper.map(department, DepartmentDto.class);
	}

	@Override
	public String deleteDepartment(String departmentCode) {

		Department department = departmentRepository.findByDepartmentCode(departmentCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(DEPARTMENT_NOT_FOUND), departmentCode)));

		departmentRepository.deleteById(department.getDepartmentId());

		return appProperties.getMessages().get(DEPARTMENT_DELETION_SUCCEEDED) + departmentCode;
	}
}
