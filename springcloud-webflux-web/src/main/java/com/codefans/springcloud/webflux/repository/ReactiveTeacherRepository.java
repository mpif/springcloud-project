package com.codefans.springcloud.webflux.repository;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:02
 */

import com.codefans.springcloud.webflux.domain.Teacher;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface ReactiveTeacherRepository extends R2dbcRepository<Teacher, Long> {
}

