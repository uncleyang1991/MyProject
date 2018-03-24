package org.uy.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.MovieDto;
import org.uy.record.page.DataTablesResult;
import org.uy.record.service.MovieService;
import org.uy.record.tools.JsonTool;

import java.util.Map;

@Controller
@RequestMapping(value="/record/movie",produces="application/json;charset=utf-8")
public class MovieController{

    @Autowired
    private MovieService movieService;

    @RequestMapping("/movieList.do")
    @ResponseBody
    public String movieList(@RequestParam Map<String,Object> params){
        DataTablesResult<MovieDto> result = movieService.findMovieBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    @RequestMapping("/addMovie.do")
    @ResponseBody
    public String addMovie(@RequestParam Map<String,Object> params){
        boolean result = movieService.addMovie(params);
        if(result){
            return JsonTool.makeResultJson(true,"添加成功");
        }
        return JsonTool.makeResultJson(false,"添加失败");
    }

    @RequestMapping("/getMovieInfo.do")
    @ResponseBody
    public String getMovieInfo(@RequestParam String id){
        if(id==null){
            return JsonTool.makeResultJson(false,"读取信息出错!");
        }
        return JsonTool.obj2json(movieService.findMovieById(id));
    }

    @RequestMapping("/updateMovie.do")
    @ResponseBody
    public String updateMovie(@RequestParam Map<String,Object> params){
        boolean result = movieService.updateMovie(params);
        if(result){
            return JsonTool.makeResultJson(true,"更新成功");
        }
        return JsonTool.makeResultJson(false,"更新失败");
    }

    @RequestMapping("/movieInfoPull.do")
    @ResponseBody
    public String movieInfoPull(@RequestParam String id){
        MovieDto movie = movieService.movieInfoPull(id);
        if(movie != null){
            return JsonTool.obj2json(movie);
        }
        return JsonTool.makeResultJson(false,"同步失败");
    }
}
