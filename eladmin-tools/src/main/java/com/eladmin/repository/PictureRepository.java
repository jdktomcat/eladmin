package com.eladmin.repository;

import com.eladmin.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author tq
 * @date 2018-12-27
 */
public interface PictureRepository extends JpaRepository<Picture,Long>, JpaSpecificationExecutor {
}
