package com.tit.employeepayrollapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private static final Logger log = LoggerFactory.getLogger(Employee.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required and cannot be empty.")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]{2,}$", message = "Name must start with a capital letter and have at least 3 characters.")
    private String name;

    private double salary;

    @NotBlank(message = "Department is required and cannot be empty.")
    private String department;

    @NotBlank(message = "Gender is required and cannot be empty.")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other.")
    private String gender;

    @NotNull(message = "Start date is required.")
    @PastOrPresent(message = "Start date must be in the past or present.")
    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDate startDate;

    @NotBlank(message = "Note cannot be empty.")
    private String note;

    @NotBlank(message = "Profile picture URL is required.")
    private String profilePic;

    public void logEmployeeCreation() {
        log.info("Employee created: {}", this);
    }
}
