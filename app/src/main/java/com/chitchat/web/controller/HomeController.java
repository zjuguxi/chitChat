package com.chitchat.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Hidden
@Controller
public class HomeController {

    @RequestMapping(path = "/")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }
}
