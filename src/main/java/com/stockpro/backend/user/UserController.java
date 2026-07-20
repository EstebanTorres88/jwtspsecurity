package com.stockpro.backend.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/user")
public class UserController {
    

    @GetMapping("/getUsers")
    public String getUsers(@RequestParam String name) {
        return name;
    }
    



}
