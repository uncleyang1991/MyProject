package org.uy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.entity.EpisodeDto;
import org.uy.entity.ResultStrDto;
import org.uy.page.DataTablesResult;
import org.uy.service.EpisodeService;
import org.uy.service.OperationService;
import org.uy.tools.JsonTool;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping(value="/operation",produces="text/html;charset=utf-8")
public class OperationController {

    @Resource
    private OperationService service;

    @RequestMapping("/operationList.do")
    @ResponseBody
    public String operationList(@RequestParam Map<String,Object> params){
        DataTablesResult<ResultStrDto> result = service.findOperationsBySearchItem(params);
        return JsonTool.obj2json(result);
    }

    public void setService(OperationService service) {
        this.service = service;
    }
}
