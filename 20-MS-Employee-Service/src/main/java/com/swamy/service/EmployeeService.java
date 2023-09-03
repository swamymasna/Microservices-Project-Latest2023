package com.swamy.service;

import java.util.List;

import com.swamy.dto.ApiResponse;
import com.swamy.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> getAllEmployees();

	ApiResponse getEmployeeById(Integer employeeId);

	EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto);

	String deleteEmployee(Integer employeeId);
	

}
