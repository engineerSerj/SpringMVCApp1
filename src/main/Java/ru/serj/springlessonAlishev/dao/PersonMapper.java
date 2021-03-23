package ru.serj.springlessonAlishev.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.serj.springlessonAlishev.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    // в методе mapRow мы должны из resultSet мы должны брать очередную строку и переводить ее в объект класса Person
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        // мы делаем здесь простую вещь, а именно устанавливаем в поле объекта персон значение из колонки таблицы в БД, причем названия колонок и полей объекта совпадают
        //мы можем использовать готовый RowMapper из библиотеки Spring jdbc - BeanPropertyRowMapper
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setEmail(resultSet.getString("email"));
        person.setAge(resultSet.getInt("age"));

        return person;
    }
}
