package com.eladmin.repository;

import com.eladmin.domain.QiniuConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tq
 * @date 2018-12-31
 */
public interface QiNiuConfigRepository extends JpaRepository<QiniuConfig,Long> {
}
