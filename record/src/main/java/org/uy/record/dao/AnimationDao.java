package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.record.entity.AnimationDto;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

import java.util.Map;

@Repository()
public interface AnimationDao extends BaseDao{

    PageResult<AnimationDto> findAnimationBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter") PageParameter parameter);

    AnimationDto findAnimationById(@Param("id") String id);
}
