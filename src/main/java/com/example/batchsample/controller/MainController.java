package com.example.batchsample.controller;

import com.example.batchsample.service.MainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {

        this.mainService = mainService;
    }

    @GetMapping("/job/first")
    public String jobAPI() throws Exception{

        mainService.firstJobLauncher();

        return "OK";
    }
}
