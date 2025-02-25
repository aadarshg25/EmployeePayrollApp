package com.tit.employeepayrollapp.service;

import com.tit.employeepayrollapp.dto.EmployeeDTO;
import com.tit.employeepayrollapp.entity.Employee;
import com.tit.employeepayrollapp.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = repository.findAll().stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    public ResponseEntity<EmployeeDTO> getEmployeeById(Long id) {
        Optional<Employee> employee = repository.findById(id);
        return employee.map(emp -> ResponseEntity.ok(modelMapper.map(emp, EmployeeDTO.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<EmployeeDTO> addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = repository.save(employee);
        return ResponseEntity.ok(modelMapper.map(savedEmployee, EmployeeDTO.class));
    }

    public ResponseEntity<EmployeeDTO> updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Update only necessary fields
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setDepartment(employeeDTO.getDepartment());

        Employee updatedEmployee = repository.save(existingEmployee);
        EmployeeDTO updatedDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);

        return ResponseEntity.ok(updatedDTO);
    }


    public ResponseEntity<Void> deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
