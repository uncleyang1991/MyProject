package org.uy.record.service;

import org.springframework.stereotype.Service;
import org.uy.record.dao.MovieDao;
import org.uy.record.entity.MovieDto;
import org.uy.record.module.MovieInfoPull;
import org.uy.record.page.DataTablesResult;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;
import org.uy.record.tools.IdTool;
import org.uy.record.tools.MapTool;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Resource
    private MovieDao movieDao;

    @Resource
    private MovieInfoPull mip;

    @Override
    public DataTablesResult<MovieDto> findMovieBySearchItem(Map<String, Object> item) {
        DataTablesResult<MovieDto> dataTablesResult = new DataTablesResult<MovieDto>();
        PageParameter parameter = new PageParameter();
        parameter.setPageSize(Integer.parseInt(item.get("length").toString()));
        parameter.setPage(Integer.parseInt(item.get("start").toString())/Integer.parseInt(item.get("length").toString())+1);
        PageResult<MovieDto> pageResult = movieDao.findMovieBySearchItem(item,parameter);
        dataTablesResult.setRecordsTotal(pageResult.getTotalRow());
        dataTablesResult.setRecordsFiltered(pageResult.getTotalRow());
        dataTablesResult.setData(pageResult);
        return dataTablesResult;
    }

    @Override
    public boolean addMovie(Map<String,Object> params) {
        params.put("id", IdTool.getUUID());
        MovieDto movie = MapTool.mapToMovie(params);
        int result = movieDao.add(movie);
        return result == 1;
    }

    @Override
    public MovieDto findMovieById(String id) {
        return movieDao.findMovieById(id);
    }

    @Override
    public boolean updateMovie(Map<String, Object> params) {
        MovieDto movie = MapTool.mapToMovie(params);
        int result = movieDao.update(movie);
        return result == 1;
    }

    @Override
    public MovieDto movieInfoPull(String id) throws Exception{
        return mip.getMovieInfo(id);
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void setMip(MovieInfoPull mip) {
        this.mip = mip;
    }
}
