package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.record.entity.MovieDto;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;

import java.util.Map;

@Repository()
public interface MovieDao extends BaseDao{

    PageResult<MovieDto> findMovieBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter") PageParameter parameter);

    MovieDto findMovieById(@Param("id") String id);
}
