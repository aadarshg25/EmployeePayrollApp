package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.entity.Employee;
import com.tit.employeepayrollapp.exception.EmployeeNotFoundException;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employees = repository.findAll().stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    public ResponseEntity<EmployeeDTO> getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(modelMapper.map(employee, EmployeeDTO.class));
    }

    public ResponseEntity<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO) {
        log.info("Adding new employee: {}", employeeDTO);

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        // Ensure departments list is correctly mapped
        employee.setDepartments(employeeDTO.getDepartments());

        Employee savedEmployee = repository.save(employee);
        return ResponseEntity.ok(modelMapper.map(savedEmployee, EmployeeDTO.class));
    }



    public ResponseEntity<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);
        Employee existingEmployee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setDepartments(employeeDTO.getDepartments());
        existingEmployee.setGender(employeeDTO.getGender());
        existingEmployee.setStartDate(employeeDTO.getStartDate());
        existingEmployee.setNote(employeeDTO.getNote());
        existingEmployee.setProfilePic(employeeDTO.getProfilePic());

        Employee updatedEmployee = repository.save(existingEmployee);
        return ResponseEntity.ok(modelMapper.map(updatedEmployee, EmployeeDTO.class));
    }


    public ResponseEntity<Void> deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
