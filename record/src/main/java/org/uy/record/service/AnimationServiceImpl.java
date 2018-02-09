package org.uy.record.service;

import org.springframework.stereotype.Service;
import org.uy.record.dao.AnimationDao;
import org.uy.record.dao.OperationDao;
import org.uy.record.entity.AnimationDto;
import org.uy.record.module.AnimationInfoPull;
import org.uy.record.page.DataTablesResult;
import org.uy.record.page.PageParameter;
import org.uy.record.page.PageResult;
import org.uy.record.tools.IdTool;
import org.uy.record.tools.MapTool;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class AnimationServiceImpl implements AnimationService {

    @Resource()
    private AnimationDao animationDao;

    @Resource
    private AnimationInfoPull aip;

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
        AnimationDto animation = MapTool.mapToAnimation(params);
        int result = animationDao.add(animation);
        return result == 1;
    }

    @Override
    public AnimationDto findAnimationById(String id) {
        return animationDao.findAnimationById(id);
    }

    @Override
    public boolean updateAnimation(Map<String, Object> params) {
        AnimationDto animation = MapTool.mapToAnimation(params);
        int result = animationDao.update(animation);
        return result == 1;
    }

    @Override
    public AnimationDto animationInfoPull(String id) throws Exception{
        return aip.getAnimationInfo(id);
    }

    public void setAnimationDao(AnimationDao animationDao) {
        this.animationDao = animationDao;
    }

    public void setAip(AnimationInfoPull aip) {
        this.aip = aip;
    }
}
