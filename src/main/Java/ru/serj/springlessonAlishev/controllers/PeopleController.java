package ru.serj.springlessonAlishev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.serj.springlessonAlishev.dao.PersonDAO;
import ru.serj.springlessonAlishev.models.Person;

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

    @GetMapping("/new") //GET запрос для создания новой формы для добавления new Person
    /*public String newPerson(Model model){
        model.addAttribute("person", new Person());*/
        //раз мы используем Thymeleaf форму то будем передавать туда объект для которого эта форма нужна
        public String newPerson(@ModelAttribute("person") Person person){
            //выше мы в методе newPerson помещаем Model а затем в нее внедряем новый пустой объект класса Person, но мы можем здесь использовать
        //@ModelAttribute потому что в этот метод мы не будем посылать полей класса Person поэтому эта аннтоация  @ModelAttribute не видит поступающих полей в поступающем GET запросе
        // и она создаст пустой объект класса Person и поместит его в модель, которому мы укажем key = "person"
        return "people/new"; //возвращаем форму HTML-шаблона лежащего по пути people/new
    }
    //метод принимает пост запрос, по POST запросу /people мы должны попасть в этот метод, здесь мы должны получить данные из формы, создать нового человека положить в него данные из формы и этого человека добавить в нашу условную БД
    @PostMapping()
    // в аннотации укажем ключ "person" и в объекте класса Person будет лежать объект с данными из формы /new
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        // используем редирект - механизм браузера который говорит перейти на какую то другую страницу
        return "redirect:/people";
    }

}
