package org.uy.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.AnimationDto;
import org.uy.record.page.DataTablesResult;
import org.uy.record.service.AnimationService;
import org.uy.record.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/animation",produces="application/json;charset=utf-8")
public class AnimationController {

    @Resource
    private AnimationService service;

    @RequestMapping("/animationList.do")
    @ResponseBody
    public String animationList(@RequestParam Map<String,Object> params){
        DataTablesResult<AnimationDto> result = service.findAnimationBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addAnimation.do")
    @ResponseBody
    public String addAnimation(@RequestParam Map<String,Object> params){
        boolean result = service.addAnimation(params);
        if(result){
            return JsonTool.makeResultJson(true,"添加成功");
        }
        return JsonTool.makeResultJson(false,"添加失败");
    }

    @RequestMapping("/getAnimationInfo.do")
    @ResponseBody
    public String getAnimationInfo(@RequestParam String id){
        if(id==null){
            return JsonTool.makeResultJson(false,"读取信息出错!");
        }
        return JsonTool.obj2json(service.findAnimationById(id));
    }

    @RequestMapping("/updateAnimation.do")
    @ResponseBody
    public String updateAnimation(@RequestParam Map<String,Object> params){
        boolean result = service.updateAnimation(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/animationInfoPull.do")
    @ResponseBody
    public String animationInfoPull(@RequestParam String id){
        AnimationDto animation;
        try{
            animation = service.animationInfoPull(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonTool.makeResultJson(false,"更新失败");
        }
        if(animation != null){
            return JsonTool.obj2json(animation);
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    public void setService(AnimationService service) {
        this.service = service;
    }
}
