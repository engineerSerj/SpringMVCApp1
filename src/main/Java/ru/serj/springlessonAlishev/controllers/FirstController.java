package ru.serj.springlessonAlishev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/calculator")
    public String webCalculator(HttpServletRequest request, Model model){
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        String result;
        String action= request.getParameter("action");
        switch (action){
            case "multiplication":
                result = String.valueOf(a*b);
                break;
            case "addition":
                result = String.valueOf(a+b);
                break;
            case "subtraction":
                result = String.valueOf(a-b);
                break;
            case "division":
                result = String.valueOf((double) a/b);
                break;
            default: result="invalid action";
        }
        model.addAttribute("result", a+" "+action+" "+b+" = "+ result);

        return "first/calculator";
    }

    //Давайте с помощью модели передадим эти параметры HTTP запроса в представление и отобразим их пользователю
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value="name", required = false) String name,
                            @RequestParam(value="surname", required = false) String surname,
                            Model model){ // добавляем в параметры model и Spring автоматически внедрит эту модель в этот метод контроллера
//теперь можем взять строчку которую здесь выводим d rjycjkm и поместить ее в модель
        //System.out.println("Hello, " + name + " " + surname);
//модель будет передана в представление и в представлении с помощью thymeleaf мы получим доступ к этой строке
//и отобразим ее пользователю в браузере
        model.addAttribute("message","Hello, " + name + " " + surname );//key=value
        return "first/hello";
    }

    /*@GetMapping("/hello")
    public String helloPage(@RequestParam(value="name", required = false) String name, //если не передадим параметры в URL получим ошибку 400 (неверный запрос) в отличие от второго способа где вернулся бы null
                            @RequestParam(value="surname", required = false) String surname){// или можем в этой аннотации использовать конструкцию @RequestParam(value="surname", required = false), это означает что если мы в гет запросе передаем параметры то эти параметры внедряються в наши переменные, если не предаем то просто передается null
        // значение которое лежит по ключу name будет положено в параметр name
        //Spring за нас возьмет значения из URL с этими ключами, положит их в эти параметры метода и эти параметры внутри метода уже будут содержать в себе значения
        System.out.println("Hello, " + name + " " + surname);
        return "first/hello";
    }*/
    /*@GetMapping("/hello")
    public String helloPage(HttpServletRequest request){// если не передадим параметры в URL, то String name и surname будут равны null
        //request этот объект будет внедрен Spring и в этом объекте содержаться все
        // сведения о поступающем HTTP запросе от пользователя
        //давайте скажем что в url пользователь должен будет предавать два параметра имя и фамилия
        String name = request.getParameter("name"); //по ключ name получаем значение и присваиваем его String name
        String surname = request.getParameter("surname");

        System.out.println("Hello, " + name + " " + surname);
        return "first/hello";
    }*/

    @GetMapping("/goodbye")
    public String goodByePage(){
        return "first/goodbye";
    }
}
