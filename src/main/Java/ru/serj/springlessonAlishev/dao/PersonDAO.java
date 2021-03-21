package ru.serj.springlessonAlishev.dao;

import org.springframework.stereotype.Component;
import ru.serj.springlessonAlishev.models.Person;

import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }
    public List<Person> index(){
        return people;
    }
    public Person show(int id) {
        //фильтруем по id и возвращаем если нашли или null
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        //кроме того мы не будем сами указывать id в форме /new, будем присваивать автоматически при добавлении в БД
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
}
