package com.codefans.springcloud.domain;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:59
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Teacher extends BaseModel {
    @NotBlank
    private String name;
    private int age;
    private String course;
}
