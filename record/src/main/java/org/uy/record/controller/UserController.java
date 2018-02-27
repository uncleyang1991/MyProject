package org.uy.record.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.UserDto;
import org.uy.record.service.UserService;
import org.uy.record.system.SystemCount;
import org.uy.record.tools.JsonTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/record/main",produces = "application/json;charset=utf-8")
public class UserController {

    private final Logger log = Logger.getLogger(UserController.class);

    @Resource
    private UserService service;

    @RequestMapping("/login.do")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpSession session){
        if(session.getAttribute("loginUser") != null){
            session.removeAttribute("loginUser");
        }
        String json = JsonTool.makeResultJson(false,"用户名或密码错误");

        if(username == null || password == null){
            //非法登录
            log.warn("有用户尝试非法登录Record系统");
            SystemCount.warnCount++;
            return json;
        }
        UserDto loginUser = service.login(username,password);
        if(loginUser != null){
            session.setAttribute("loginUser",loginUser);
            json = JsonTool.makeResultJson(true,loginUser.getId()+"@"+loginUser.getUsername()+"@"+loginUser.getNickname());
            SystemCount.loginCount++;
            SystemCount.totalLoginCount++;
        }else{
            //用户名或密码错误
            log.warn("有用户尝试用错误的用户名或密码登录，该用户输入的用户名为："+username+"，密码为："+password);
            SystemCount.warnCount++;
        }
        return json;
    }

    @RequestMapping("/checkLogin.do")
    @ResponseBody
    public String checkLogin(HttpSession session){
        String json = JsonTool.makeResultJson(false,"");
        if(session.getAttribute("loginUser") != null){
            json = JsonTool.makeResultJson(true,"");
        }
        return json;
    }

    public void setService(UserService service) {
        this.service = service;
    }

}
