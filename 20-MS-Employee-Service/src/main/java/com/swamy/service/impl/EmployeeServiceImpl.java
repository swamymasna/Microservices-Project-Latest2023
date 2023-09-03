package com.swamy.service.impl;

import static com.swamy.utils.AppConstants.EMPLOYEE_DELETION_SUCCEEDED;
import static com.swamy.utils.AppConstants.EMPLOYEE_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.swamy.client.DepartmentClient;
import com.swamy.client.OrganizationClient;
import com.swamy.dto.ApiResponse;
import com.swamy.dto.DepartmentDto;
import com.swamy.dto.EmployeeDto;
import com.swamy.dto.OrganizationDto;
import com.swamy.entity.Employee;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.props.AppProperties;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;

	private ModelMapper modelMapper;

	private AppProperties appProperties;

	private DepartmentClient departmentClient;

	private OrganizationClient organizationClient;

	private RestTemplate restTemplate;

	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

		Employee employee = null;

		employee = modelMapper.map(employeeDto, Employee.class);

		employee = employeeRepository.save(employee);

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employees = employeeRepository.findAll();

		return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());
	}

	@CircuitBreaker(name = "EMPLOYEE-SERVICE", fallbackMethod = "defaultGetEmployeeById")
	public ApiResponse getEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
				String.format(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND), employeeId)));
		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

		// Logic to Fetch Department Data
		DepartmentDto departmentDto = departmentClient.getDepartmentByCode(employee.getDepartmentCode());

		// Logic to Fetch Organization Data
		OrganizationDto organizationDto = organizationClient.getOrganizationByCode(employee.getOrganizationCode());

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setEmployeeDto(employeeDto);
		apiResponse.setDepartmentDto(departmentDto);
		apiResponse.setOrganizationDto(organizationDto);

		return apiResponse;
	}

	public ApiResponse defaultGetEmployeeById(Integer employeeId, Exception exception) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
				String.format(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND), employeeId)));

		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentId(1);
		departmentDto.setDepartmentName("DEFAULT-DEPT");
		departmentDto.setDepartmentCode("DEFAULT-DEPT-001");
		departmentDto.setDepartmentDescription("DEFAULTDEPT--DESC");

		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setOrganizationId(1);
		organizationDto.setOrganizationName("DEFAULT-ORG");
		organizationDto.setOrganizationCode("DEFAULT-ORG-001");
		organizationDto.setOrganizationDescription("DEFAULT-ORG-DESC");

		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setEmployeeDto(employeeDto);
		apiResponse.setDepartmentDto(departmentDto);
		apiResponse.setOrganizationDto(organizationDto);

		return apiResponse;
	}

	@Override
	public EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto) {

		Employee existingEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND), employeeId)));

		existingEmployee.setEmployeeName(employeeDto.getEmployeeName());
		existingEmployee.setEmployeeSalary(employeeDto.getEmployeeSalary());
		existingEmployee.setEmployeeAddress(employeeDto.getEmployeeAddress());

		Employee employee = employeeRepository.save(existingEmployee);

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public String deleteEmployee(Integer employeeId) {

		Employee existingEmployee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(appProperties.getMessages().get(EMPLOYEE_NOT_FOUND), employeeId)));

		employeeRepository.deleteById(existingEmployee.getEmployeeId());

		return appProperties.getMessages().get(EMPLOYEE_DELETION_SUCCEEDED) + employeeId;
	}

}
