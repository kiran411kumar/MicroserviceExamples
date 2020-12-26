package com.nemana.user.service;

import com.nemana.user.entity.User;
import com.nemana.user.vo.Department;
import reactor.core.publisher.Flux;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User addUser(User user);

    User getUserById(Long id);
}
