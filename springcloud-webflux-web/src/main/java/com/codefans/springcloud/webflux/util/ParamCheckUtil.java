package com.codefans.springcloud.webflux.util;

import com.codefans.springcloud.webflux.domain.BizException;
import com.codefans.springcloud.webflux.domain.Student;
import com.codefans.springcloud.webflux.domain.Teacher;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: codefans
 * @Date: 2022-08-03 16:44
 */
public class ParamCheckUtil {
    public static void checkSaveStudent(Student student){
        if (StringUtils.isBlank(student.getName())){
            throw new BizException("name can not be blank");
        }
        if (StringUtils.isBlank(student.getAge())){
            throw new BizException("age can not be blank");
        }
    }

    public static void checkSaveTeacher(Teacher teacher) {
        if (StringUtils.isBlank(teacher.getName())){
            throw new BizException("name can not be blank");
        }

    }
}

