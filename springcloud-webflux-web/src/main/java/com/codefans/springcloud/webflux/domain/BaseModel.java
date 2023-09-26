package com.codefans.springcloud.webflux.domain;

/**
 * @Author: codefans
 * @Date: 2022-08-02 23:40
 */

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

// 基础类
@MappedSuperclass
public class BaseModel implements Serializable {
    /**
     * 主键id，且设置为自增
     */
    @Id
    protected Long id;

    public LocalDateTime createTime;

    protected LocalDateTime updateTime;

    protected short isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }
}
