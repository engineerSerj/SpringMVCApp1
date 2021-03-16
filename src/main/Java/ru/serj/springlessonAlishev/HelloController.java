package ru.serj.springlessonAlishev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // в этой аннотации мы указываем какой url будет приходить в этот метод
    @GetMapping("/hello-world")
    public String sayHello() {
// просто вернем пользователю представление
        return "hello_world";
    }
}
