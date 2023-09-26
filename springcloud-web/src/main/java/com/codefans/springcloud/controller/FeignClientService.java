package com.codefans.springcloud.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: codefans
 * @Date: 2022-09-20 16:53
 */
@FeignClient("feignClientService")
public interface FeignClientService {

    @GetMapping("/user/{id}")
    String findById(@PathVariable("id") Long id);
}
