package zoo;

import org.junit.jupiter.api.*;

import javax.sql.RowSet;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainTest {
    private static Connection connection;

    @BeforeAll
    static void initConnection() {
        String url = "jdbc:postgresql:forPractice";
        String user = "postgres";
        String password = "qscxzsew322";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterAll
    static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void checkConnection(){
        try {
            Assertions.assertTrue(connection.isValid(1));
            Assertions.assertFalse(connection.isClosed());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void selectCarnivoreAnimals(){
        try(Statement statement = connection.createStatement()){
            statement.execute("SELECT * FROM zoo WHERE type = 'carnivore'");
            try(ResultSet result = statement.getResultSet()) {
                while (result.next())
                    Assertions.assertTrue(result.getString(2).equals("carnivore"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
