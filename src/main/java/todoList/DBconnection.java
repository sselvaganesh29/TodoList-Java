package todoList;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection
{

    private static final String URL = "jdbc:postgresql://localhost:5432/todolist";

    private static final String USERNAME = "superuser";

    private static final String PASSWORD  = "selva";

    public static Connection getDbConnection() throws Exception
    {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

}
