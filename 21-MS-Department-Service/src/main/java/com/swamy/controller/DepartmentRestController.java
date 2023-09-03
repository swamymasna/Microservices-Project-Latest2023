package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.swamy.dto.DepartmentDto;
import com.swamy.service.DepartmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@AllArgsConstructor
public class DepartmentRestController {

	private DepartmentService departmentService;

	@PostMapping
	public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
		return new ResponseEntity<>(departmentService.saveDepartment(departmentDto), HttpStatus.CREATED);
	}

	@GetMapping("/{dept-code}")
	public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable("dept-code") String departmentCode) {
		return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
		return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
	}

	@PutMapping("/{dept-code}")
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("dept-code") String departmentCode,
			DepartmentDto departmentDto) {
		return new ResponseEntity<>(departmentService.updateDepartment(departmentCode, departmentDto), HttpStatus.OK);
	}

	@DeleteMapping("/{dept-code}")
	public ResponseEntity<String> deleteDepartment(@PathVariable("dept-code") String departmentCode) {
		return new ResponseEntity<>(departmentService.deleteDepartment(departmentCode), HttpStatus.OK);
	}
}
