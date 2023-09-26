package com.codefans.springcloud.webflux;

import com.codefans.springcloud.webflux.handler.StudentHandler;
import com.codefans.springcloud.webflux.handler.TeacherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;


/**
 * @Author: codefans
 * @Date: 2022-08-02 17:37
 */

@Configuration
public class UrlRouter {
    @Bean
    RouterFunction<ServerResponse> studentRouter(StudentHandler studentHandler){
        // 此处就相当于原来StudentController上面的@RequestMapping("/student")
        return nest(path("/studentRouter"),
                // 下面就相当于是原来StudentController类中的各个方法上的@RequestMapping("/add")
                route(POST("/add").and(accept(MediaType.APPLICATION_JSON)), studentHandler::add)
                        .andRoute(POST("/update").and(accept(MediaType.APPLICATION_JSON)), studentHandler::add)
                        .andRoute(POST("/delete/{id}"), studentHandler::delete)
                        .andRoute(GET("/queryById/{id}"), studentHandler::queryById)
                        .andRoute(GET("/queryAll").and(accept(MediaType.APPLICATION_JSON)), studentHandler::queryAll)
        );
    }

    @Bean
    RouterFunction<ServerResponse> teacherRouter(TeacherHandler teacherHandler){
        // 此处就相当于原来TeacherController上面的@RequestMapping("/teacher")
        return nest(path("/teacherRouter"),
                // 下面就相当于是原来TeacherController类中的各个方法上的@RequestMapping("/add")
                route(POST("/add").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::add)
                        .andRoute(POST("/update").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::add)
                        .andRoute(POST("/delete").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::delete)
                        .andRoute(POST("/queryById").and(accept(MediaType.APPLICATION_JSON)), teacherHandler::queryById)
                        .andRoute(GET("/queryAll"), teacherHandler::queryAll)
        );
    }
}
