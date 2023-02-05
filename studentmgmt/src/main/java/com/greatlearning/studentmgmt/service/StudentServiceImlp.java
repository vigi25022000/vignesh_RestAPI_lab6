package com.greatlearning.studentmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.studentmgmt.entity.Student;
import com.greatlearning.studentmgmt.repository.StudentRepository;

@Service
public class StudentServiceImlp implements StudentService {
	
	@Autowired
	StudentRepository repository;
	
	@Override
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	@Override
	public void addOrUpdate (Student student) {
		repository.save(student);
	}

	@Override
	public void deleteByStudentId(int id) {
		repository.deleteById(id);
	}

	@Override
	public Student getStudentById(int id) {
		return repository.findById(id).get();
	}

}