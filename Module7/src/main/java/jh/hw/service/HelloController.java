package jh.hw.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

//    @GetMapping("/hello")
    @GetMapping("/hello2")
    public String hello(Model model) {

        model.addAttribute("name", "John Doe");

        return "welcome";
    }
}