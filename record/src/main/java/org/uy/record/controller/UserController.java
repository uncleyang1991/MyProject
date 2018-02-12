package org.uy.record.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.uy.record.entity.UserDto;
import org.uy.record.service.UserService;
import org.uy.record.tools.DateFormatTool;
import org.uy.record.tools.JsonTool;
import org.uy.record.tools.MailTool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "/record/main",produces = "application/json;charset=utf-8")
public class UserController {

    @Resource
    private UserService service;

    @Resource
    private MailTool mailTool;

    @RequestMapping("/login.do")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpSession session){
        if(session.getAttribute("loginUser") != null){
            session.removeAttribute("loginUser");
        }
        String json = JsonTool.makeResultJson(false,"口令错误");

        if(username == null || password == null){
            //非法登录
            mailTool.sendMail("记录平台登录告警","　　有用户于"+ DateFormatTool.dateToStr("yyyy-MM-dd HH:mm:ss",new Date())+"尝试非法登录记录平台。");
            return json;
        }
        UserDto loginUser = service.login(username,password);
        if(loginUser != null){
            session.setAttribute("loginUser",loginUser);
            json = JsonTool.makeResultJson(true,loginUser.getId()+"@"+loginUser.getUsername()+"@"+loginUser.getNickname());
        }else{
            //密码错误
            mailTool.sendMail("记录平台登录告警","　　有用户于"+ DateFormatTool.dateToStr("yyyy-MM-dd HH:mm:ss",new Date())+"尝试用错误口令登录，该用户输入的口令为："+password);
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

    public void setMailTool(MailTool mailTool) {
        this.mailTool = mailTool;
    }
}
