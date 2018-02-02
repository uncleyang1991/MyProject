package org.uy.service;

import org.uy.entity.MovieDto;
import org.uy.page.DataTablesResult;

import java.util.Map;

public interface MovieService {

    DataTablesResult<MovieDto> findMovieBySearchItem(Map<String, Object> item);

    boolean addMovie(Map<String, Object> params);

    MovieDto findMovieById(String id);

    boolean updateMovie(Map<String, Object> params);

    MovieDto movieInfoPull(String id) throws Exception;
}
