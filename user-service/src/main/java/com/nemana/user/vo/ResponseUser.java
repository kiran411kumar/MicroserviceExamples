package com.nemana.user.vo;

import com.nemana.user.entity.User;
import lombok.Data;

@Data
public class ResponseUser {
    private User user;
    private Department department;
}
