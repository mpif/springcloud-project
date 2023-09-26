drop database if EXISTS springcloud_testdb;
create database springcloud_testdb CHARACTER SET utf8 COLLATE utf8_general_ci;

use springcloud_testdb;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
    `is_deleted` smallint(0) NOT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
    `age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `grade` int(0) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `sex` smallint(0) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
    `id` bigint(0) NOT NULL AUTO_INCREMENT,
    `create_time` datetime(0) NULL DEFAULT NULL,
    `is_deleted` smallint(0) NOT NULL,
    `update_time` datetime(0) NULL DEFAULT NULL,
    `age` int(0) NOT NULL,
    `course` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;
