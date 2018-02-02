package org.uy.service;

import org.springframework.stereotype.Service;
import org.uy.base.page.PageParameter;
import org.uy.base.page.PageResult;
import org.uy.dao.AnimationDao;
import org.uy.entity.AnimationDto;
import org.uy.module.AnimationInfoPull;
import org.uy.page.DataTablesResult;
import org.uy.tools.DateFormatTool;
import org.uy.tools.IdTool;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AnimationServiceImpl implements AnimationService {

    @Resource()
    private AnimationDao animationDao;

    @Resource
    private AnimationInfoPull eip;

    @Override
    public DataTablesResult<AnimationDto> findAnimationBySearchItem(Map<String, Object> item) {
        DataTablesResult<AnimationDto> dataTablesResult = new DataTablesResult<AnimationDto>();
        PageParameter parameter = new PageParameter();
        parameter.setPageSize(Integer.parseInt(item.get("length").toString()));
        parameter.setPage(Integer.parseInt(item.get("start").toString())/Integer.parseInt(item.get("length").toString())+1);
        PageResult<AnimationDto> pageResult = animationDao.findAnimationBySearchItem(item,parameter);
        dataTablesResult.setRecordsTotal(pageResult.getTotalRow());
        dataTablesResult.setRecordsFiltered(pageResult.getTotalRow());
        dataTablesResult.setData(pageResult);
        return dataTablesResult;
    }

    @Override
    public boolean addAnimation(Map<String,Object> params) {
        params.put("id", IdTool.getUUID());
        AnimationDto animation = mapToAnimation(params);
        int result = animationDao.add(animation);
        return result == 1;
    }

    @Override
    public AnimationDto findAnimationById(String id) {
        return animationDao.findAnimationById(id);
    }

    @Override
    public boolean updateAnimation(Map<String, Object> params) {
        AnimationDto animation = mapToAnimation(params);
        int result = animationDao.update(animation);
        return result == 1;
    }

    @Override
    public AnimationDto animationInfoPull(String id) throws Exception{
        return eip.getAnimationInfo(id);
    }

    /**
     * map转换为AnimationDto
     * @param params
     * @return
     */
    private AnimationDto mapToAnimation(Map<String, Object> params){
        AnimationDto animation = new AnimationDto();
        animation.setId(params.get("id").toString());
        animation.setName(params.get("name").toString());
        animation.setType(params.get("type").toString());
        animation.setTotal(Integer.parseInt(params.get("total").toString()));
        animation.setDramaType(params.get("dramaType").toString());
        animation.setIntroduce(params.get("introduce").toString());
        animation.setIsEnd(params.get("state").toString());
        animation.setLevel(Integer.parseInt(params.get("level").toString()));
        animation.setPerformers(params.get("performers").toString());
        animation.setWriter(params.get("writer").toString());
        animation.setWatchState(params.get("watchState").toString());
        String showTime = params.get("showTime").toString();
        if(!"".equals(showTime)){
            animation.setShowTime(DateFormatTool.strToDate(null,showTime));
        }
        String watchTime = params.get("watchTime").toString();
        if(!"".equals(watchTime)){
            animation.setWatchTime(DateFormatTool.strToDate(null,watchTime));
        }
        return animation;
    }

    public void setAnimationDao(AnimationDao animationDao) {
        this.animationDao = animationDao;
    }

}
