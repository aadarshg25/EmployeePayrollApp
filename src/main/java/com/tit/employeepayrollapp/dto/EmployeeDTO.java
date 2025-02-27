package com.tit.employeepayrollapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.Date;

@Data
public class EmployeeDTO {

    @NotBlank(message = "Name is required and cannot be empty.")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]{2,}$", message = "Name must start with a capital letter and have at least 3 characters.")
    private String name;

    private double salary;
    private String department;
    private String gender;
    private Date startDate;
    private String note;
    private String profilePic;
}

