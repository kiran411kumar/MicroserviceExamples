package com.nemana.department.service;

import com.nemana.department.entity.Department;
import com.nemana.department.repository.DepartmentRepository;
import com.nemana.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {

        Optional<Department> dept = departmentRepository.findById(id);
        if(dept.isPresent()) {
            return dept.get();
        }
        return null;
    }
}
