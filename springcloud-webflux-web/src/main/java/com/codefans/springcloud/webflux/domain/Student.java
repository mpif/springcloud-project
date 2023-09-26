package com.codefans.springcloud.webflux.domain;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:48
 */

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table("student")
public class Student extends BaseModel {
    @Id
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String age;
    private int grade;
    private short sex;
    private String className;

}
