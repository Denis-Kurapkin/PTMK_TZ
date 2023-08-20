package testTask;

import testTask.dao.PersonDao;
import testTask.dao.impl.PersonDaoImpl;
import testTask.util.ConnectionFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws Exception {

        PersonDao personDao = new PersonDaoImpl();
        System.out.println("Hello world!");

        Connection connection = ConnectionFactory.getConnection();
        System.out.println(connection);


        switch (args[0]) {
            case ("1") -> personDao.createTable();
            case ("2") -> personDao.createPerson(args);
            case ("3") -> personDao.selectDistinctInfoAndBirthDate();
            case ("4") -> personDao.insert();
            case ("5") -> personDao.selectPersonByGenderAndInfoUsingStartsWith();
            case ("6") -> personDao.selectPersonByGenderAndInfoUsingLike();
            default -> System.out.println("Введите аргументы от 1 до 5");
        }
    }
}