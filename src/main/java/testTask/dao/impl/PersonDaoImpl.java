package testTask.dao.impl;

import org.apache.commons.io.IOUtils;
import testTask.dao.PersonDao;
import testTask.model.Person;
import testTask.util.ConnectionFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonDaoImpl implements PersonDao {

    private Connection connection;

    public PersonDaoImpl() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void createTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS person" +
                "(id serial primary key," +
                "firstName VARCHAR(255)," +
                "secondName VARCHAR(255)," +
                "birthDate DATE," +
                "sex VARCHAR(1))";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createPerson(String[] args) throws ParseException {
        String sqlQuery = "INSERT INTO PERSON (firstName, secondName, birthDate, sex) values (?, ?, ?, ?)";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = simpleDateFormat.parse(args[3]);
        java.sql.Date sqlDate = new Date(date.getTime());

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, args[1]);
            statement.setString(2, args[2]);
            statement.setDate(3, sqlDate);
            statement.setString(4, args[4]);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void selectDistinctInfoAndBirthDate() {
        String sqlQuery = "SELECT firstName, secondName, birthDate, sex, extract(YEAR FROM NOW()) - date_part('year', birthDate) as age from person where firstName in (select firstName from person group by firstName having count(firstName)=1) and secondName in (select secondName from person group by secondName having count(secondName)=1) and birthDate in (select birthDate from person group by birthDate having count(birthDate) = 1) order by firstName, secondName";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            Person person = null;
            while (resultSet.next()) {
                person = new Person(
                        resultSet.getString("firstName"),
                        resultSet.getString("secondName"),
                        resultSet.getDate("birthDate"),
                        resultSet.getInt("age"),
                        resultSet.getString("sex"));
                System.out.println(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/main/resources/LongQuery.txt");
        String sqlQuery = IOUtils.toString(inputStream, "UTF-8");

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.execute();
            statement.close();
            System.out.println("Finished");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void selectPersonByGenderAndInfoUsingStartsWith() {
        String sqlQuery = "select concat(person.firstName, ' ', person.secondName), person.birthDate, person.sex from person where sex = 'M' and starts_with(concat(person.firstName, ' ', person.secondName), 'F')";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            long t1 = System.currentTimeMillis();
            statement.execute();
            System.out.println(System.currentTimeMillis() - t1 + " ms");
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void selectPersonByGenderAndInfoUsingLike() {
        String sqlQuery = "select concat(person.firstName, ' ', person.secondName), person.birthDate, person.sex from person where sex = 'M' and concat(person.firstName, ' ', person.secondName) like 'F%'";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            long t1 = System.currentTimeMillis();
            statement.execute();
            System.out.println(System.currentTimeMillis() - t1 + " ms");
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
