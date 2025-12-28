package com.zhuru.repository;

import com.zhuru.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据ID和未删除查询用户
     */
    User findByIdAndDeletedFalse(Long id);

    /**
     * 根据用户名和未删除查询用户
     */
    User findByUsernameAndDeletedFalse(String username);
}