package org.uy.base.dao;

import org.apache.ibatis.annotations.Param;
import org.uy.base.dto.BaseDto;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;

import java.util.List;
import java.util.Map;

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
