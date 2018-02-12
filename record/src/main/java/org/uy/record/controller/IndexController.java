package org.uy.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.uy.record.entity.UserDto;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/record")
    public String index(){
        return "redirect:/record/login.html";
    }

}
