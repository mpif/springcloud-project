package com.codefans.springcloud.controller;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:37
 */

import com.codefans.springcloud.common.domain.Result;
import com.codefans.springcloud.domain.Student;
import com.codefans.springcloud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

// student的controller
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public Result add(@RequestBody @Valid Student student) {
        return Result.success(studentService.add(student));
    }

    @PostMapping("/update")
    public Result update(@Valid Student student) {
        return Result.success(studentService.update(student));
    }

    @PostMapping("/delete")
    public Result delete(@Valid Student student) {
        return Result.success(studentService.delete(student));
    }

    @PostMapping("/queryById")
    public Result queryById(@NotNull Long id) {
        return Result.success(studentService.queryById(id));
    }

    @GetMapping("/queryAll")
    public Result queryAll() {
        return Result.success(studentService.queryAll());
    }

    @PostMapping("/queryAll")
    public Result queryAll(@Valid Student student) {
        return Result.success(studentService.queryAll(student));
    }
}
