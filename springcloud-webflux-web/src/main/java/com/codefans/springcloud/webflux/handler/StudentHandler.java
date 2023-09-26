package com.codefans.springcloud.webflux.handler;

import com.codefans.springcloud.webflux.domain.Result;
import com.codefans.springcloud.webflux.domain.Student;
import com.codefans.springcloud.webflux.repository.ReactiveStudentRepository;
import com.codefans.springcloud.webflux.util.ParamCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.server.ServerResponse.*;


/**
 * @Author: codefans
 * @Date: 2022-08-03 16:43
 */


@Component
public class StudentHandler {
    @Autowired
    private ReactiveStudentRepository reactiveStudentRepository;

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        Mono<Student> student = serverRequest.bodyToMono(Student.class);
        return student.flatMap(stu -> {
            // 自定义的参数校验
            ParamCheckUtil.checkSaveStudent(stu);

            LocalDateTime now = LocalDateTime.now();
            stu.setCreateTime(now);
            Mono<Student> resultMono = reactiveStudentRepository.save(stu);
//            return reactiveStudentService.add(student).flatMap(stu -> Mono.just(Result.success(stu)));

            return ok().contentType(MediaType.APPLICATION_JSON)
                    .body(resultMono, Result.class);
        });

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        // 1. 获取路径中的id
        String idStr = serverRequest.pathVariable("id");
        Long id = Long.valueOf(idStr);
        // 2. 首先查询出来在数据库中的数据
        return reactiveStudentRepository.findById(id)
                // 2.1 数据存在，则执行后续删除操作
                .flatMap(inDb ->
                        reactiveStudentRepository.deleteById(id)
                                // 删除成功，则将删除前的数据转换为统一响应数据结构返回
                                .then(ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(Result.success(inDb)),Result.class)))
                // 2.2 数据不存在，则直接返回统一响应数据错误格式返回
                .switchIfEmpty(ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(Result.fail("未找到对应的数据")), Result.class));
    }

    public Mono<ServerResponse> queryById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ok().contentType(MediaType.APPLICATION_JSON)
                // 执行成功后，转换为统一响应数据结构返回
                .body(reactiveStudentRepository.findById(Mono.just(Long.valueOf(id)))
                        .flatMap(stu -> Mono.just(Result.success(stu))), Result.class);
    }

    public Mono<ServerResponse> queryAll(ServerRequest serverRequest) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                // 执行成功后，转换为统一响应数据结构返回
                .body(reactiveStudentRepository.findAll()
                        .collectList().flatMap(stuList -> Mono.just(Result.success(stuList))), Result.class);
    }

}
