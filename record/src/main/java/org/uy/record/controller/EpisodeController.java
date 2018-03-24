package org.uy.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.EpisodeDto;
import org.uy.record.page.DataTablesResult;
import org.uy.record.service.EpisodeService;
import org.uy.record.tools.JsonTool;

import java.util.Map;

@Controller
@RequestMapping(value="/record/episode",produces="application/json;charset=utf-8")
public class EpisodeController{

    @Autowired
    private EpisodeService episodeService;

    @RequestMapping("/episodeList.do")
    @ResponseBody
    public String episodeList(@RequestParam Map<String,Object> params){
        DataTablesResult<EpisodeDto> result = episodeService.findEpisodeBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addEpisode.do")
    @ResponseBody
    public String addEpisode(@RequestParam Map<String,Object> params){
        boolean result = episodeService.addEpisode(params);
        if(result){
            return JsonTool.makeResultJson(true,"添加成功");
        }
        return JsonTool.makeResultJson(false,"添加失败");
    }

    @RequestMapping("/getEpisodeInfo.do")
    @ResponseBody
    public String getEpisodeInfo(@RequestParam String id){
        if(id==null){
            return JsonTool.makeResultJson(false,"读取信息出错!");
        }
        return JsonTool.obj2json(episodeService.findEpisodeById(id));
    }

    @RequestMapping("/updateEpisode.do")
    @ResponseBody
    public String updateEpisode(@RequestParam Map<String,Object> params){
        boolean result = episodeService.updateEpisode(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/episodeInfoPull.do")
    @ResponseBody
    public String episodeInfoPull(@RequestParam String id){
        EpisodeDto episode = episodeService.episodeInfoPull(id);
        if(episode != null){
            return JsonTool.obj2json(episode);
        }
        return JsonTool.makeResultJson(false,"同步失败");
    }
}
