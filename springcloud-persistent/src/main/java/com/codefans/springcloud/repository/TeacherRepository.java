package com.codefans.springcloud.repository;

/**
 * @Author: codefans
 * @Date: 2022-08-03 0:02
 */

import com.codefans.springcloud.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}

