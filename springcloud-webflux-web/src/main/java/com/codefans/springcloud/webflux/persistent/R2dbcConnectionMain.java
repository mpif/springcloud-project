package com.codefans.springcloud.webflux.persistent;

import com.alibaba.fastjson.JSON;
import com.codefans.springcloud.webflux.domain.Student;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.netty.util.internal.StringUtil;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

import java.util.List;
import com.codefans.springcloud.webflux.domain.Result;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;


/**
 * @Author: codefans
 * @Date: 2022-08-04 15:09
 */

public class R2dbcConnectionMain {

    private String url;

    private String username;

    private String password;

    @Resource
    private ConnectionFactory connectionFactory;

    private static final String STUDENT = "STUDENT";
    private static final String USER = "USER";
    private static final String PASSWORD = "PASSWORD";

    public static void main(String[] args) {
        R2dbcConnectionMain rcm = new R2dbcConnectionMain();
        rcm.r2dbcConn();
    }

    private void r2dbcConn() {

//        url = "r2dbc:mysql://localhost:3306/springcloud_testdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";
//        username = "root";
//        password = "";

        url = "r2dbcs:mysql://root:@localhost:3306/springcloud_testdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";

        // Notice: the query string must be URL encoded
        ConnectionFactory connectionFactory = ConnectionFactories.get(url);
        Mono<Connection> connectionMono = Mono.from(connectionFactory.create());
//        connectionMono.flatMap(conn -> conn.createStatement("select * from student").execute());
//        connectionMono.map(conn -> {
//            Statement statement = conn.createStatement("select * from student");
//            Mono<Student> monoResult = Mono.from(statement.execute()).flatMap(res );
//        })

        Mono<?> stuListMono = connectionMono.flatMapMany(it -> it.createStatement("SELECT * FROM student").execute()).collectList();
//        .flatMapMany(stuList -> Mono.just(Result.success(stuList)), Result.class);

//        connectionMono.flatMapMany(it -> it.createStatement("SELECT * FROM student").execute()).collectList().flatMap(stuList -> Mono.just(com.codefans.springcloud.webflux.domain.Result.success(stuList))), Result.class));//.flatMap(it -> it.map((row, rowMetadata) -> collectToMap(row, rowMetadata)));
        System.out.println(JSON.toJSON(stuListMono));

//        return ok().contentType(MediaType.APPLICATION_JSON)
//                // 执行成功后，转换为统一响应数据结构返回
//                .body(reactiveStudentRepository.findAll()
//                        .collectList().flatMap(stuList -> Mono.just(com.codefans.springcloud.webflux.domain.Result.success(stuList))), com.codefans.springcloud.webflux.domain.Result.class);


    }

//    @Bean
    public ConnectionFactory getConnectionFactory() {
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(url);
        ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
        if (!StringUtil.isNullOrEmpty(username)) {
            ob = ob.option(Option.valueOf(USER), username);
        }
        if (!StringUtil.isNullOrEmpty(password)) {
            ob = ob.option(Option.valueOf(PASSWORD), password);
        }
        return ConnectionFactories.get(ob.build());
    }

    @Bean
    ConnectionFactory connectionFactory() {
        return MySqlConnectionFactory.from(MySqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .port(3306)
                .username("root")
                .password("123456")
                .database("database_name")
                // 额外的其它非必选参数省略
                .build());
    }


    private Mono<Integer> executeSql(DatabaseClient client, String sql) {
        return client.sql(sql).fetch().rowsUpdated();
    }

    public Flux<Student> findAll() {

        Mono count = Mono.from(connectionFactory.create())

                .flatMapMany(it ->it.createStatement("select count(id) from student").execute())
                .flatMap(io.r2dbc.spi.Result::getRowsUpdated)

                .next();

        Flux<Student> rows = Mono.from(connectionFactory.create())

                .flatMapMany(it -> it.createStatement("SELECT * FROM student").execute())

                .flatMap(it -> it.map((row, rowMetadata) -> collectToMap(row, rowMetadata)));
        return rows;

//        return Mono.from(connectionFactory.create())
//                .flatMap((c) -> Mono.from(c.createStatement("select * from student")
//                                .execute())
////                        .doFinally((st) -> close(c)))
//                        .doFinally())
//                .flatMapMany(result -> Flux.from(result.map((row, meta) -> {
//                    Student stu = new Student();
//                    stu.setId(row.get("id", Long.class));
//                    stu.setName(row.get("name", String.class));
//                    stu.setAge(row.get("age", String.class));
//                    return stu;
//                })));
    }

    private Student collectToMap(Row row, RowMetadata rowMetadata) {
        Student stu = new Student();
        stu.setId(row.get("id", Long.class));
        stu.setName(row.get("name", String.class));
        System.out.println(stu);
        return stu;
    }
//    public Mono<Users> findById(long id) {
//
//        return Mono.from(connectionFactory.create())
//                .flatMap(c -> Mono.from(c.createStatement("select id,firstname,lastname from Users where id = $1")
//                                .bind("$1", id)
//                                .execute())
//                        .doFinally((st) -> close(c)))
//                .map(result -> result.map((row, meta) ->
//                        new Users(row.get("id", Long.class),
//                                row.get("firstname", String.class),
//                                row.get("lastname", String.class))))
//                .flatMap( p -> Mono.from(p));
//    }


}
