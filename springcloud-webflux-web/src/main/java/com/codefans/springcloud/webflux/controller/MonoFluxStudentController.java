package com.codefans.springcloud.webflux.controller;

import com.codefans.springcloud.webflux.domain.Result;
import com.codefans.springcloud.webflux.domain.Student;
import com.codefans.springcloud.webflux.service.ReactiveStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @Author: codefans
 * @Date: 2022-08-04 0:03
 */
@RestController
@RequestMapping("/student")
public class MonoFluxStudentController {
    @Resource
    private ReactiveStudentService reactiveStudentService;

    @PostMapping("/add")
    public Mono<Result> add(@Valid @RequestBody Student student) {
        LocalDateTime now = LocalDateTime.now();
        student.setCreateTime(now);
        return reactiveStudentService.add(student).flatMap(stu -> Mono.just(Result.success(stu)));
    }

    @PostMapping("/route")
    public Mono<Result> route(@Valid @RequestBody Student student) {
        LocalDateTime now = LocalDateTime.now();
        student.setCreateTime(now);
        return reactiveStudentService.add(student).flatMap(stu -> Mono.just(Result.success(stu)));
    }

    @PostMapping("/update")
    public Mono<Result> update(@RequestBody @Valid Student student) {
        return reactiveStudentService.update(student).flatMap(stu -> Mono.just(Result.success(stu)));
    }

    @PostMapping("/delete")
    public Mono<Result> delete(@RequestBody Student student) {
        return reactiveStudentService.delete(student).flatMap(stu -> Mono.just(Result.success(stu)));
    }

    @PostMapping("/queryById")
    public Mono<Result> queryById(Long id) {
        return reactiveStudentService.queryById(id).flatMap(stu -> Mono.just(Result.success(stu)));
    }

    @GetMapping(value = "/queryAll")
    public Mono<Result> queryAll() {
        long start = System.currentTimeMillis();
        Flux<Student> result = reactiveStudentService.queryAll();
        System.out.println("MonoFluxStudentController.queryAll : " + (System.currentTimeMillis() - start));
        // 对于service层返回的数据为List类型时，这里需要额外做一步collectList()操作，
        // 否则响应对象将会是类似于List<Result>的格式，而不是Result<List>的格式
        return result.collectList().flatMap(students -> Mono.just(Result.success(students)));
    }

    @PostMapping(value = "/queryAll")
    public Mono<Result> queryAll(@RequestBody Student student) {
        long start = System.currentTimeMillis();
        Flux<Student> result = reactiveStudentService.queryAll(student);
        System.out.printf("queryAll : " + (System.currentTimeMillis() - start));
        // 对于service层返回的数据为List类型时，这里需要额外做一步collectList()操作，
        // 否则响应对象将会是类似于List<Result>的格式，而不是Result<List>的格式
        return result.collectList().flatMap(students -> Mono.just(Result.success(students)));
    }
}
