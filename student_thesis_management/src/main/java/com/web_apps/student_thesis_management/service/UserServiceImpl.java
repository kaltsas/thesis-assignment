package com.web_apps.student_thesis_management.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web_apps.student_thesis_management.dao.ProfessorDAO;
import com.web_apps.student_thesis_management.dao.StudentDAO;
import com.web_apps.student_thesis_management.dao.UserDAO;
import com.web_apps.student_thesis_management.model.Professor;
import com.web_apps.student_thesis_management.model.Student;
import com.web_apps.student_thesis_management.model.User;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDAO.save(user);	
        if(user.getRole().getValue().equals("Professor")) {
        	Professor professor = new Professor(user);
        	professorDAO.save(professor);
        }else if(user.getRole().getValue().equals("Student")) {
        	Student student = new Student(user);
        	studentDAO.save(student);
        }
    }

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 return userDAO.findByUsername(username).orElseThrow(
	                ()-> new UsernameNotFoundException(
	                        String.format("USER_NOT_FOUND", username)
	                ));
	}
}
