package com.web_apps.student_thesis_management.service;

import org.springframework.stereotype.Service;

import com.web_apps.student_thesis_management.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
}
