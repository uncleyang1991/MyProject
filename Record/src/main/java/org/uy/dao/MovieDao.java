package org.uy.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.base.dao.BaseDao;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.entity.MovieDto;

import java.util.Map;

@Repository()
public interface MovieDao extends BaseDao{

    PageResult<MovieDto> findMovieBySearchItem(@Param("item") Map<String, Object> item, @Param("pageParameter") PageParameter parameter);

    MovieDto findMovieById(@Param("id") String id);
}
