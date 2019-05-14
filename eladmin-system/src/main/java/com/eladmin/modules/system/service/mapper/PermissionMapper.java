package com.eladmin.modules.system.service.mapper;

import com.eladmin.modules.system.domain.Permission;
import com.eladmin.mapper.EntityMapper;
import com.eladmin.modules.system.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author tq
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
