package com.eladmin.modules.system.service;

import com.eladmin.modules.system.service.dto.DictDTO;
import com.eladmin.modules.system.domain.Dict;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
* @author tq
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "dict")
public interface DictService {

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDTO create(Dict resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(Dict resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}