package com.example.playwright;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping(value = "/", produces = "text/html")
    @ResponseBody
    public String home() {
        return """
                <!doctype html>
                <html lang="en">
                <head><meta charset="utf-8"><title>Spring Boot + Playwright</title></head>
                <body><main><h1>Spring Boot is running</h1></main></body>
                </html>
                """;
    }

    @GetMapping(value = "/greeting")
    @ResponseBody
    public String getGreeting(@RequestParam String name) {
        return new String("Hello " + name);
    }

}
