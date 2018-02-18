package org.uy.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/record")
    public String index(){
        return "forward:/main.html";
    }
}
