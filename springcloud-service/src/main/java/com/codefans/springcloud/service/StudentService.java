package com.codefans.springcloud.service;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:03
 */

import com.codefans.springcloud.domain.Student;
import com.codefans.springcloud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student add(Student student) {
        return studentRepository.save(student);
    }

    public Student update(Student student) {
        return studentRepository.saveAndFlush(student);
    }

    public Student delete(Student student) {
        studentRepository.delete(student);
        return student;
    }

    public Student queryById(Long id) {
        return studentRepository.getOne(id);
    }

    public List<Student> queryAll() {
        return studentRepository.findAll();
    }

    public List<Student> queryAll(Student student) {
        Example<Student> example = Example.of(student);
        return studentRepository.findAll(example);
    }
}

