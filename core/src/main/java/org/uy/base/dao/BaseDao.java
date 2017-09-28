package org.uy.base.dao;

import org.uy.base.dto.BaseDto;

public interface BaseDao {

    int update(BaseDto dto);

    int add(BaseDto dto);

    int delete(String id);
}
