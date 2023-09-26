package com.codefans.springcloud.webflux.controller;

import com.codefans.springcloud.webflux.domain.Teacher;
import com.codefans.springcloud.webflux.repository.ReactiveTeacherRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @Author: codefans
 * @Date: 2022-08-04 0:06
 */
@RestController
@RequestMapping("/teacher")
public class MonoFluxTeacherController {
    @Resource
    private ReactiveTeacherRepository reactiveTeacherRepository;

    @PostMapping("/add")
    public Mono<Teacher> add(@Valid @RequestBody Teacher teacher) {
        LocalDateTime now = LocalDateTime.now();
        teacher.setCreateTime(now);
        return reactiveTeacherRepository.save(teacher);
    }
    @PostMapping("/update")
    public Mono<Teacher> update(@Valid @RequestBody Teacher teacher) {
        return reactiveTeacherRepository.save(teacher);
    }
    @PostMapping("/delete")
    public Mono<Teacher> delete(@Valid @RequestBody Teacher teacher) {
        reactiveTeacherRepository.delete(teacher);
        return Mono.just(teacher);
    }
    @PostMapping("/queryById")
    public Mono<Teacher> queryById(Long id) {
        return reactiveTeacherRepository.findById(id);
    }
    @GetMapping("/queryAll")
    public Flux<Teacher> queryAll() {
        return reactiveTeacherRepository.findAll();
    }
    @PostMapping("/queryAll")
    public Flux<Teacher> queryAll(@Valid @RequestBody Teacher teacher) {
        return reactiveTeacherRepository.findAll();
    }

}
