package org.uy.record.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.uy.record.dao.OperationDao;
import org.uy.record.entity.AnimationDto;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.entity.MovieDto;
import org.uy.record.entity.OperationDto;
import org.uy.record.tools.IdTool;
import org.uy.record.tools.MapTool;

import javax.annotation.Resource;
import java.util.Map;

@Aspect
@Component
public class OperationAspect {

    @Resource
    private OperationDao operationDao;

    @Around("execution(public * org.uy.record.service.*Impl.update*(..)) || execution(public * org.uy.record.service.*Impl.add*(..))")
    public Object operation(ProceedingJoinPoint pjp){
        String methodName = pjp.getSignature().getName();
        try{
            boolean result = (boolean)pjp.proceed();
            if(!result){
                return false;
            }
            int index = 0;
            if((index = methodName.lastIndexOf("Episode")) != -1){
                EpisodeDto episode = MapTool.mapToEpisode((Map<String,Object>)pjp.getArgs()[0]);
                operationDao.add(new OperationDto(IdTool.getUUID(),"剧集",episode.getName(),methodName.substring(0,index)));
            }else if((index = methodName.lastIndexOf("Movie"))!=-1){
                MovieDto movie = MapTool.mapToMovie((Map<String,Object>)pjp.getArgs()[0]);
                operationDao.add(new OperationDto(IdTool.getUUID(),"电影",movie.getName(),methodName.substring(0,index)));
            }else if((index = methodName.lastIndexOf("Animation"))!=-1){
                AnimationDto animation = MapTool.mapToAnimation((Map<String,Object>)pjp.getArgs()[0]);
                operationDao.add(new OperationDto(IdTool.getUUID(),"动漫",animation.getName(),methodName.substring(0,index)));
            }
        }catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }
}
