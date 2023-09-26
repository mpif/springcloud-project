package com.codefans.springcloud.controller;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:06
 */

import com.codefans.springcloud.common.domain.Result;
import com.codefans.springcloud.domain.Teacher;
import com.codefans.springcloud.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

// teacher的controller
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/add")
    public Result add(@Valid Teacher teacher) {
        return Result.success(teacherService.add(teacher));
    }

    @PostMapping("/update")
    public Result update(@Valid Teacher teacher) {
        return Result.success(teacherService.update(teacher));
    }

    @PostMapping("/delete")
    public Result delete(@Valid Teacher teacher) {
        return Result.success(teacherService.delete(teacher));
    }

    @PostMapping("/queryById")
    public Result queryById(@NotNull Long id) {
        return Result.success(teacherService.queryById(id));
    }

    @GetMapping("/queryAll")
    public Result queryAll() {
        return Result.success(teacherService.queryAll());
    }

    @PostMapping("/queryAll")
    public Result queryAll(@Valid Teacher teacher) {
        return Result.success(teacherService.queryAll(teacher));
    }
}

