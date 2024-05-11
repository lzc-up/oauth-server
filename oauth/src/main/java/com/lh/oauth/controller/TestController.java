package com.lh.oauth.controller;

import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    @GetMapping
    public String hello() {
        log.info("---test---");
        return "hello";
    }


}

