package com.codefans.springcloud.webflux.handler;

import com.codefans.springcloud.webflux.domain.Result;
import com.codefans.springcloud.webflux.domain.Student;
import com.codefans.springcloud.webflux.domain.Teacher;
import com.codefans.springcloud.webflux.repository.ReactiveTeacherRepository;
import com.codefans.springcloud.webflux.util.ParamCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;


/**
 * @Author: codefans
 * @Date: 2022-08-03 16:44
 */


@Component
public class TeacherHandler {
    @Autowired
    private ReactiveTeacherRepository reactiveTeacherRepository;

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        Mono<Teacher> Teacher = serverRequest.bodyToMono(Teacher.class);
        return Teacher.flatMap(tea -> {
            // 参数校验
            ParamCheckUtil.checkSaveTeacher(tea);

//            Mono<Result> resultMono = reactiveTeacherRepository.saveAll(Teacher)
//                    .collectList().flatMap(teaList -> Mono.just(Result.success(teaList.get(0))));

            LocalDateTime now = LocalDateTime.now();
            tea.setCreateTime(now);
            Mono<Teacher> resultMono = reactiveTeacherRepository.save(tea);

            return ok().contentType(MediaType.APPLICATION_JSON)
                    .body(resultMono, Result.class);
        });

    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String idStr = serverRequest.pathVariable("id");
        Long id = Long.valueOf(idStr);
        return reactiveTeacherRepository.findById(id)
                .flatMap(inDb ->
                        reactiveTeacherRepository.deleteById(id)
                                .then(ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(Result.success(inDb)),Result.class)))
                .switchIfEmpty(ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(Result.fail("未找到对应的数据")), Result.class));
    }

    public Mono<ServerResponse> queryById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(reactiveTeacherRepository.findById(Mono.just(Long.valueOf(id)))
                        .flatMap(tea -> Mono.just(Result.success(tea))), Result.class);
    }

    public Mono<ServerResponse> queryAll(ServerRequest serverRequest) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(reactiveTeacherRepository.findAll()
                        .collectList().flatMap(teaList -> Mono.just(Result.success(teaList))), Result.class);
    }

}
