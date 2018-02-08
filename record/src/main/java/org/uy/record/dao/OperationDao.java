package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.record.entity.OperationDto;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

import java.util.Map;

@Repository()
public interface OperationDao extends BaseDao{

    PageResult<OperationDto> findOperationBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter") PageParameter parameter);
}
