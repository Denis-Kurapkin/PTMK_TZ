package testTask.dao;

import java.io.IOException;
import java.text.ParseException;

public interface PersonDao {

    void createTable();

    void createPerson(String[] args) throws ParseException;

    void selectDistinctInfoAndBirthDate();

    void insert() throws IOException;

    void selectPersonByGenderAndInfoUsingStartsWith();

    void selectPersonByGenderAndInfoUsingLike();
}
