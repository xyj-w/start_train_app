package com.zhuru.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * 基础实体类
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * 创建时间
     */
    @Column(name = "created_at", updatable = false)
    private Long createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * 标记删除
     */
    private Boolean deleted = false;

    /**
     * 持久化前设置创建时间
     */
    @PrePersist
    public void prePersist() {
        Long currentTime = System.currentTimeMillis();
        if (this.createdAt == null) {
            this.createdAt = currentTime;
        }
        if (this.updatedAt == null) {
            this.updatedAt = currentTime;
        }
        if (this.deleted == null) {
            this.deleted = false;
        }
    }

    /**
     * 更新前设置更新时间
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}