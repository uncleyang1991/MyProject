package org.uy.record.service;

import org.uy.record.entity.AnimationDto;
import org.uy.record.page.DataTablesResult;

import java.util.Map;

public interface AnimationService {

    DataTablesResult<AnimationDto> findAnimationBySearchItem(Map<String, Object> item);

    boolean addAnimation(Map<String, Object> params);

    AnimationDto findAnimationById(String id);

    boolean updateAnimation(Map<String, Object> params);

    AnimationDto animationInfoPull(String id);
}
