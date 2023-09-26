package com.codefans.springcloud.webflux.service;

import com.codefans.springcloud.webflux.domain.Student;
import com.codefans.springcloud.webflux.repository.ReactiveStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Author: codefans
 * @Date: 2022-08-03 23:58
 */
@Service
public class ReactiveStudentService {

    @Resource
    private ReactiveStudentRepository reactiveStudentRepository;

    public Mono<Student> add(Student student) {
        return reactiveStudentRepository.save(student);
    }

    public Mono<Student> update(Student student) {
        return reactiveStudentRepository.save(student);
    }

    public Mono<Student> delete(Student student) {
        reactiveStudentRepository.delete(student);
        return Mono.just(student);
    }

    public Mono<Student> queryById(Long id) {
        return reactiveStudentRepository.findById(id);
    }

    public Flux<Student> queryAll() {
        return reactiveStudentRepository.findAll();
    }

    public Flux<Student> queryAll(Student student) {
        return reactiveStudentRepository.findAll();
    }

}
