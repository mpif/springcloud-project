package com.codefans.springcloud.webflux.service;

import com.codefans.springcloud.webflux.domain.Teacher;
import com.codefans.springcloud.webflux.repository.ReactiveTeacherRepository;
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
public class ReactiveTeacherService {

    @Resource
    private ReactiveTeacherRepository reactiveTeacherRepository;

    public Mono<Teacher> add(Teacher teacher) {
        return reactiveTeacherRepository.save(teacher);
    }

    public Mono<Teacher> update(Teacher teacher) {
        return reactiveTeacherRepository.save(teacher);
    }

    public Mono<Teacher> delete(Teacher teacher) {
        reactiveTeacherRepository.delete(teacher);
        return Mono.just(teacher);
    }

    public Mono<Teacher> queryById(Long id) {
        return reactiveTeacherRepository.findById(id);
    }

    public Flux<Teacher> queryAll() {
        return reactiveTeacherRepository.findAll();
    }

    public Flux<Teacher> queryAll(Teacher teacher) {
        return reactiveTeacherRepository.findAll();
    }
}
