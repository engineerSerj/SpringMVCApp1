package ru.serj.springlessonAlishev.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.serj.springlessonAlishev.dao.PersonDAO;
import ru.serj.springlessonAlishev.models.Person;

import javax.validation.Valid;

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
        //@ModelAttribute потому что в этот метод мы не будем посылать полей класса Person поэтому эта аннтация  @ModelAttribute не видит поступающих полей в поступающем GET запросе(мы же их не передаем)
        // и она создаст пустой объект класса Person и поместит его в модель, которому мы укажем key = "person"
        return "people/new"; //возвращаем форму HTML-шаблона лежащего по пути people/new
    }
    //метод принимает пост запрос, по POST запросу /people мы должны попасть в этот метод, здесь мы должны получить данные из формы, создать нового человека положить в него данные из формы и этого человека добавить в нашу условную БД
    @PostMapping()
    // в аннотации укажем ключ "person" и в объекте класса Person будет лежать объект с данными из формы /new
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        //@Valid - проверяет что при создании Spring'ом объекта person и помещении его в модель, данные из формы соответствуют условиям описанным аннотациями в классе Person над полями
        // если условия валидации нарушаются то появляется ошибка и эта ошибка помещается в отдельный объект BindingResult, важно учесть что этот объект должен идти следующим аргументом метода после @Valid аргумента
        //именно в этот объект BindingResult будет внедрен тот объект с ошибками валидации класса который стоит аргументом перед ним
        if(bindingResult.hasErrors()){//проверяем есть ли ошибки в этом объекте
            return "people/new"; //если ошибки есть то не идем дальше, а сразу опять возвращаем форму people/new  возвращаем ту самую форму  для создания нового объекта
           // но в этой форме уже будут ошибки и они будут показываться с помощью thymeleaf; почему? потому что в этом объекте person уже содержатся ошибки которые получились в результате валидации значений с формы
            //и т.к. @ModelAttribute автоматически добавляет объект в модель, то когда мы вернем форму "people/new" на ней будет объект класса Person и у него будут ошибки которые внедрились автоматически при помощи аннотации @Valid и мы покажем их с помощью таймлифа
        }
        personDAO.save(person);
        // используем редирект - механизм браузера который говорит перейти на какую то другую страницу
        return "redirect:/people";
    }
    //Создадим метод возвращающий страницу для редактирования человека
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
            model.addAttribute("person", personDAO.show(id));//внедряем в модель человека с этим id , его будем отображать на форме редактирования и к нему будет доступ в нашем представлении, чтобы в самой форме поля уже небыли пустыми
            return "people/edit";
    }
    @PatchMapping("/{id}")//чтобы сюда направлялся запрос нам нужно создать тот самый фильтр в классе конфигурации MySpringDispatcherServletInitialaizer
    public String update(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id){
     //принимаем с помощью @ModelAttribute("person")объект person из формы, @PathVariable("id") принимает id из адреса
     //теперь мы должны найти человека с таким id в бд и заменить его значения на те что пришли из формы, на те что лежат в person полученном с помощью @ModelAttribute
      // это будем делать внутри DAO
        if(bindingResult.hasErrors()){// то сразу возвращаем страницу "people/edit" ту самую страницу для редактирования человека
            return "people/edit";//Он обрабатывает представление и отправляет HTML клиенту
        }
        personDAO.update(id, person);
        return  "redirect:/people";//Он возвращается клиенту (браузеру), который интерпретирует ответ HTTP и автоматически вызывает URL-адрес перенаправления;Перенаправление: медленнее, браузер клиента задействован, браузер отображает перенаправленный URL-адрес, он создает новый запрос на перенаправленный URL-адрес.
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
            personDAO.delete(id);
            return "redirect:/people";
    }

}
