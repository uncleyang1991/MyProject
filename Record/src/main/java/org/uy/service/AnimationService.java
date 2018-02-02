package org.uy.service;

import org.uy.entity.AnimationDto;
import org.uy.page.DataTablesResult;

import java.util.Map;

public interface AnimationService {

    DataTablesResult<AnimationDto> findAnimationBySearchItem(Map<String, Object> item);

    boolean addAnimation(Map<String, Object> params);

    AnimationDto findAnimationById(String id);

    boolean updateAnimation(Map<String, Object> params);

    AnimationDto animationInfoPull(String id) throws Exception;
}
