package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.uy.record.entity.BaseDto;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

/**
 * Dao层基础接口
 */
public interface BaseDao {

    <T extends BaseDto> PageResult<T> find(PageParameter parameter);

    <T extends  BaseDto> T findById(@Param("id") String id);

    int add(@Param("obj") BaseDto obj);

    int update(@Param("obj") BaseDto obj);

    int delete(@Param("id") String id);
}
