package com.codefans.springcloud.webflux.repository;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:02
 */

import com.codefans.springcloud.webflux.domain.Student;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public interface ReactiveStudentRepository extends R2dbcRepository<Student, Long> {
    @Query("SELECT * FROM student")
    Flux<Student> findAll();
}

