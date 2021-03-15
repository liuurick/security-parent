package com.liuurick.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liubin
 */
@Controller
public class MainController {
    @RequestMapping({"/index","/",""})
    public String index(){
        return "index";
    }
}
