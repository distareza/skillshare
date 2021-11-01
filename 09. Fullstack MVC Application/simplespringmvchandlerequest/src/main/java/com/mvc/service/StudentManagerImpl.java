package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.StudentDAO;
import com.mvc.model.Student;

@Service
public class StudentManagerImpl implements StudentManager {

	@Autowired
	public StudentDAO studentDAO;

	public List<Student> getStudents() {
		return studentDAO.getStudents();
	}

}
