package org.uy.base.dao;

import org.uy.base.dto.BaseDto;

import java.util.List;
import java.util.Map;

/**
 * Dao层基础接口
 */
public interface BaseDao {

    List<BaseDto> find(Map<String,Object> params);

    BaseDto findById(String id);

    int add(BaseDto obj);

    int update(BaseDto obj);

    int delete(String id);
}
