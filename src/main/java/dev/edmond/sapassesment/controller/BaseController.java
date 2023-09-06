package dev.edmond.sapassesment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BaseController {

    @GetMapping("")
    public String getHomePage(){
        return "index";
    }
}

