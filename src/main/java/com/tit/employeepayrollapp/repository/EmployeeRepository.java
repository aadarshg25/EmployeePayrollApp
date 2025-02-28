package com.tit.employeepayrollapp.repository;

import com.tit.employeepayrollapp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE :department MEMBER OF e.departments")
    List<Employee> findEmployeesByDepartment(@Param("department") String department);
}