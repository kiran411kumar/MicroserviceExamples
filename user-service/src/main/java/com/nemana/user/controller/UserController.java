package com.nemana.user.controller;

import com.nemana.user.entity.User;
import com.nemana.user.service.UserService;
import com.nemana.user.vo.Department;
import com.nemana.user.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public Flux<ResponseUser> getAllUser() {
        log.info("Inside getAllUsers");
        List<User> users = userService.getAllUsers();
        List<ResponseUser> responseUsersList = new ArrayList<>();
        for (User user : users) {
            ResponseUser responseUser = new ResponseUser();
            responseUser.setUser(user);
            Department dept = restTemplate.getForObject("http://DEPARTMENT-SERVICE/dept/" + user.getDeptId(), Department.class);
            responseUser.setDepartment(dept);
            responseUsersList.add(responseUser);
        }
        return Flux.fromIterable(responseUsersList);
    }

    @PostMapping("/add")
    public Mono<User> addUser(@RequestBody User user) {
        log.info("Inside addUser");
        User addedUser = userService.addUser(user);
        log.info("User added successfully:-"+addedUser);
        return Mono.just(addedUser);
    }
    @GetMapping("/{id}")
    public Mono<ResponseUser> getUserAndDeptById(@PathVariable("id") Long id) {
        log.info("Inside getUserAndDeptById");
        User us = userService.getUserById(id);
        Department dept = restTemplate.getForObject("http://DEPARTMENT-SERVICE/dept/" + us.getDeptId(), Department.class);
        ResponseUser res = new ResponseUser();
        res.setUser(us);
        res.setDepartment(dept);
        return Mono.just(res);
    }



}
