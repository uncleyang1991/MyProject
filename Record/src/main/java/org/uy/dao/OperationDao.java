package org.uy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.base.dao.BaseDao;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.entity.AnimationDto;
import org.uy.entity.OperationDto;

import java.util.Map;

@Repository()
public interface OperationDao extends BaseDao{

    PageResult<OperationDto> findOperationBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter") PageParameter parameter);
}
