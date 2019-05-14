package com.eladmin.repository;

import com.eladmin.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tq
 * @date 2018-12-26
 */
public interface EmailRepository extends JpaRepository<EmailConfig,Long> {
}
