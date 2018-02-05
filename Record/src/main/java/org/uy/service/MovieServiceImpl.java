package org.uy.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.dao.MovieDao;
import org.uy.dao.OperationDao;
import org.uy.entity.MovieDto;
import org.uy.entity.OperationDto;
import org.uy.module.MovieInfoPull;
import org.uy.page.DataTablesResult;
import org.uy.tools.DateFormatTool;
import org.uy.tools.IdTool;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {

    @Resource()
    private MovieDao movieDao;

    @Resource
    private OperationDao operationDao;

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
    @Transactional
    public boolean addMovie(Map<String,Object> params) {
        params.put("id", IdTool.getUUID());
        MovieDto movie = mapToMovie(params);
        int result = movieDao.add(movie)+operationDao.add(new OperationDto(IdTool.getUUID(),"电影",movie.getName(),"add"));
        return result == 2;
    }

    @Override
    @Transactional
    public MovieDto findMovieById(String id) {
        return movieDao.findMovieById(id);
    }

    @Override
    public boolean updateMovie(Map<String, Object> params) {
        MovieDto movie = mapToMovie(params);
        int result = movieDao.update(movie)+operationDao.add(new OperationDto(IdTool.getUUID(),"电影",movie.getName(),"update"));
        return result == 2;
    }

    @Override
    public MovieDto movieInfoPull(String id) throws Exception{
        return mip.getMovieInfo(id);
    }

    /**
     * map转换为MovieDto
     * @param params
     * @return
     */
    private MovieDto mapToMovie(Map<String, Object> params){
        MovieDto movie = new MovieDto();
        movie.setId(params.get("id").toString());
        movie.setName(params.get("name").toString());
        movie.setType(params.get("type").toString());
        movie.setDuration(Integer.parseInt(params.get("duration").toString()));
        movie.setRegion(params.get("region").toString());
        movie.setIntroduce(params.get("introduce").toString());
        movie.setLevel(Integer.parseInt(params.get("level").toString()));
        movie.setPerformers(params.get("performers").toString());
        movie.setDirector(params.get("director").toString());
        String showTime = params.get("showTime").toString();
        if(!"".equals(showTime)){
            movie.setShowTime(DateFormatTool.strToDate(null,showTime));
        }
        String watchTime = params.get("watchTime").toString();
        if(!"".equals(watchTime)){
            movie.setWatchTime(DateFormatTool.strToDate(null,watchTime));
        }
        return movie;
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    public void setMip(MovieInfoPull mip) {
        this.mip = mip;
    }
}
