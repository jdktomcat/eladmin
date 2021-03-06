package com.eladmin.modules.system.service.mapper;

import com.eladmin.mapper.EntityMapper;
import com.eladmin.modules.system.domain.Job;
import com.eladmin.modules.system.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author tq
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {

}