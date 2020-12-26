package com.nemana.user.service;

import com.nemana.user.entity.User;
import com.nemana.user.respository.UserRepository;
import com.nemana.user.vo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> us = userRepository.findById(id);
        if(us.isPresent()) {
            return us.get();
        }
        return null;
    }
}
