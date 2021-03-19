package ru.serj.springlessonAlishev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.serj.springlessonAlishev.dao.PersonDAO;

@Controller
@RequestMapping("/people") //все адреса в контроллере будут начинаться с /people
public class PeopleController {

    private PersonDAO personDAO;
    //внедряем бин PersonDAO
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        // получим всех людей из DAO и передадим в представление
        //в model под именем переменной "people" кладем список людей вызывая метод из класса DAO
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
@GetMapping("/{id}") //метод в самом запросе будет принимать id
// когда запустим приложение, сюда{id} можно будет поместить любое число
//и это число поместится в аргументы метода id
// с помощью @PathVariable("id") мы извлечем id из URL и получим к нему доступ внутри метода
    public String show(@PathVariable("id") int id, Model model){
        //получим одного человека по его id из DAO и передадим на отображение в представление
    model.addAttribute("person", personDAO.show(id));
        return "people/show"; // возвращаем представление с переменой модели person на странице
    }
}
