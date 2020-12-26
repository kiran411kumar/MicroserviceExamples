package com.nemana.department.service;

import com.nemana.department.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department addDepartment(Department department);
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
}
