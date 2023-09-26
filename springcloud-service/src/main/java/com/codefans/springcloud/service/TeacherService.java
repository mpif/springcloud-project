package com.codefans.springcloud.service;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:03
 */

import com.codefans.springcloud.domain.Teacher;
import com.codefans.springcloud.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher add(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    public Teacher delete(Teacher teacher) {
        teacherRepository.delete(teacher);
        return teacher;
    }

    public Teacher queryById(Long id) {
        return teacherRepository.getOne(id);
    }

    public List<Teacher> queryAll() {
        return teacherRepository.findAll();
    }

    public List<Teacher> queryAll(Teacher teacher) {
        Example<Teacher> example = Example.of(teacher);
        return teacherRepository.findAll(example);
    }
}

