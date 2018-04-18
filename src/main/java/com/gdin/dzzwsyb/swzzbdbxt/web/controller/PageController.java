package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图控制器,返回jsp视图给前端
 * 
 **/
@Controller
@RequestMapping("/page")
public class PageController {

    /**
     * 登录页
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * dashboard页
     */
    @RequestMapping("/dashboard")
    public String dashboard() {
        return "query";
    }

    /**
     * 404页
     */
    @RequestMapping("/404")
    public String error404() {
        return "404";
    }

    /**
     * 401页
     */
    @RequestMapping("/401")
    public String error401() {
        return "401";
    }

    /**
     * 500页
     */
    @RequestMapping("/500")
    public String error500() {
        return "500";
    }

    /**
     * admin页
     */
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     * info页
     */
    @RequestMapping("/info")
    public String info() {
        return "info";
    }

    /**
     * mine页
     */
    @RequestMapping("/mine")
    public String mine() {
        return "mine";
    }
    
    /**
     * roleList页
     */
    @RequestMapping("/roleList")
    public String roleList() {
        return "roleList";
    }
    
    /**
     * addList页
     */
    @RequestMapping("/addList")
    public String addList() {
        return "addList";
    }
    
    /**
     * codeMeun页
     */
    @RequestMapping("/codeMeun")
    public String codeMeun() {
        return "codeMeun";
    }
    
    /**
     * codeList页
     */
    @RequestMapping("/codeList")
    public String codeList() {
        return "codeList";
    }
    
    /**
     * query页
     */
    @RequestMapping("/query")
    public String query() {
        return "query";
    }
    
    /**
     * msgList页
     */
    @RequestMapping("/msgList")
    public String msgList() {
        return "msgList";
    }
    
    /**
     * msgStatusName页
     */
    @RequestMapping("/msgStatusName")
    public String msgStatusName() {
        return "msgStatusName";
    }
    
    /**
     * template页
     */
    @RequestMapping("/template")
    public String template() {
        return "template";
    }

}