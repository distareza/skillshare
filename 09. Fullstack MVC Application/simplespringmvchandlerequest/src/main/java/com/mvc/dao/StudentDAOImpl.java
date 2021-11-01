package com.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mvc.model.Student;

@Repository
public class StudentDAOImpl implements StudentDAO {

	public List<Student> getStudents() {
		List<Student> studentList = new ArrayList<Student>();
		
		studentList.add(new Student("Greg", "Johnson", "Computer Science"));
		studentList.add(new Student("Alice", "Kruger", "Philosophy"));
		studentList.add(new Student("Peter", "Smith", "Math"));
		studentList.add(new Student("Claudia", "Wallace", "Chemistry"));
		studentList.add(new Student("Sinead", "O' Connor", "Operations"));
		
		return studentList;
	}

}
