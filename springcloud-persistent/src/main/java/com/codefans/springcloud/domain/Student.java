package com.codefans.springcloud.domain;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:48
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Student extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String age;
    private int grade;
    private short sex;
    private String className;

}
