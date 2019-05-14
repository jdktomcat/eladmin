package com.eladmin.modules.system.service.mapper;

import com.eladmin.modules.system.domain.Menu;
import com.eladmin.mapper.EntityMapper;
import com.eladmin.modules.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
