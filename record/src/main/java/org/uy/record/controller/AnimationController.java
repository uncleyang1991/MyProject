package org.uy.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.AnimationDto;
import org.uy.record.page.DataTablesResult;
import org.uy.record.service.AnimationService;
import org.uy.record.tools.JsonTool;

import java.util.Map;

@Controller
@RequestMapping(value="/record/animation",produces="application/json;charset=utf-8")
public class AnimationController{

    @Autowired
    private AnimationService animationService;

    @RequestMapping("/animationList.do")
    @ResponseBody
    public String animationList(@RequestParam Map<String,Object> params){
        DataTablesResult<AnimationDto> result = animationService.findAnimationBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addAnimation.do")
    @ResponseBody
    public String addAnimation(@RequestParam Map<String,Object> params){
        boolean result = animationService.addAnimation(params);
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
        return JsonTool.obj2json(animationService.findAnimationById(id));
    }

    @RequestMapping("/updateAnimation.do")
    @ResponseBody
    public String updateAnimation(@RequestParam Map<String,Object> params){
        boolean result = animationService.updateAnimation(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/animationInfoPull.do")
    @ResponseBody
    public String animationInfoPull(@RequestParam String id){
        AnimationDto animation = animationService.animationInfoPull(id);
        if(animation != null){
            return JsonTool.obj2json(animation);
        }
        return JsonTool.makeResultJson(false,"同步失败");
    }

    @RequestMapping("/deleteAnimation.do")
    @ResponseBody
    public String deleteAnimation(@RequestParam String id){
        boolean flag = animationService.deleteAnimation(id);
        if(flag){
            return JsonTool.makeResultJson(true,"删除完成");
        }
        return JsonTool.makeResultJson(false,"删除失败");
    }
}
