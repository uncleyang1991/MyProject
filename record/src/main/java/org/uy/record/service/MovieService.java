package org.uy.record.service;


import org.uy.record.entity.MovieDto;
import org.uy.record.page.DataTablesResult;

import java.util.Map;

public interface MovieService {

    DataTablesResult<MovieDto> findMovieBySearchItem(Map<String, Object> item);

    boolean addMovie(Map<String, Object> params);

    MovieDto findMovieById(String id);

    boolean updateMovie(Map<String, Object> params);

    MovieDto movieInfoPull(String id) throws Exception;
}
