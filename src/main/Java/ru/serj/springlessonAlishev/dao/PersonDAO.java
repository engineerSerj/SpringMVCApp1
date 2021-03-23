package ru.serj.springlessonAlishev.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.serj.springlessonAlishev.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {

    private  final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        //будем делать запрос с помощью jdbcTemplate используя метод query
        // (первым аргументом передаем наш запрос, вторым - RowMapper)
        //RowMapper это такой объект который отображает строки из таблице в наши сущности,
        // т.е. каждую строку, полученную в результате нашего запроса, из нашей таблицы Person он отобразит в объект класса Person
        //RowMapper мы реализуем сами в новом классе PersonMapper реализующем интерфейс RowMapper (из пакета org.springframework.jdbc.core) переопределяем единственный метод который позволяет нам из ResultSet через сеттер самостоятельно установить поля в наш объект Person(который мы создадим в методе) и вернуть его
        // передаем новый new PersonMapper()
        //или используем BeanPropertyRowMapper(передавая ему наш класс (Person) к объектам которого будет производиться перевод строк из нашей таблицы) из библиотеки Spring jdbc поскольку там реализован перевод строк таблицы по названиям колонок в поля объекта если названия полей и колонок совпадают
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        //jdbcTemplate по умолчанию использует preparedStatement(безопасны для SQL инъекций) и ждет массив значений для "?", в нашем случае массив из одного id
        //при вызове метода query возвращается список, а нам нужен только один челоек, поидее в этом списке и должен быть один человек ведь в таблице БД все id уникальны
        //и если строка есть в таблице то список вернется с 1 человеком, если такого id нет то вернется пустой список
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);//findAny() извлекает случайный объект из потока (нередко так же первый) возвращает Optional
    }

    public void save(Person person){
        // в методе update вторым аргументом идет varArg потому все наши значения будут восприниматься как массив из элементов, передадим через геттеры все значения объекта зукыщт
       jdbcTemplate.update("INSERT INTO Person VALUES(1,?,?,?)",person.getName(), person.getAge(), person.getEmail());
    }
    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id );
    }
    public void delete(int id){
       jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
