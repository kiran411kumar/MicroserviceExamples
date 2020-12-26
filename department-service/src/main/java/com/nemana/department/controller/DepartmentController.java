package com.nemana.department.controller;


import com.nemana.department.entity.Department;
import com.nemana.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/dept")
@Slf4j
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add")
    public Mono<Department> addDepartment(@RequestBody Department department) {
        log.info("inside addDepartment");
        Department dept = departmentService.addDepartment(department);
        return Mono.just(dept);
    }
    @GetMapping("/all")
    public Flux<Department> getAllDepartments() {
        log.info("inside getAllDepartments");
        List<Department> deptList = departmentService.getAllDepartments();
        return Flux.fromIterable(deptList);
    }

    @GetMapping("/{id}")
    public Mono<Department> getDepartmentById(@PathVariable("id") Long id) {
        log.info("Inside getDepartmentById");
        Department dept = departmentService.getDepartmentById(id);
        return dept == null? Mono.empty() : Mono.just(dept);
    }

}
