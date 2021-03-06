package com.eladmin.modules.system.service.mapper;

import com.eladmin.mapper.EntityMapper;
import com.eladmin.modules.system.domain.DictDetail;
import com.eladmin.modules.system.service.dto.DictDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author tq
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}