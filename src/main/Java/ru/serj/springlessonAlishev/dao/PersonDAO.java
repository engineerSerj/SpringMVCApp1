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

        people.add(new Person(++PEOPLE_COUNT, "Tom",24,"tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 52,"bob@mail.ru" ));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 18,"mike@yahoo.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 34,"katy@gmail.ru"));
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
    public void update(int id, Person updatedPerson){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }
    public void delete(int id){
        people.removeIf(person -> person.getId()==id);// условие при котором мы хотим удалять объект из листа, если выражение тру то удаляем, проходимся по списку и если предикат возвращает тру то удаляем
    }
}
