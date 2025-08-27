package com.example.demoSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello-public")
    public String helloPublic() {
        return "Hello public";
    }

    @GetMapping("/hello-private")
    public String helloPrivate() {
        return "Hello privé";
    }
}
