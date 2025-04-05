package todoList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoListManager {


    public static  final TodoListManager INSTANCE = new TodoListManager();

    private TodoListManager()
    {
    }


    public List<Task> getAllTask() throws Exception {

        String qry = "SELECT * FROM task WHERE user_Id = ? ";

        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(qry))
        {

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {

                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    boolean status = rs.getBoolean("status");
                    String date = (String) rs.getString("date");
                    String priority = rs.getString("priority");

                    taskList.add(new Task(id,task,status,date,priority));
                }

                return taskList;

            }

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }


    public List<Task> getCompletedTaskOrNonCompletedTask(String getTaskStatus) throws Exception {
        String qry = "SELECT * FROM task WHERE status = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(qry))
        {
            boolean b = true;

            if ( getTaskStatus.equals("completedTask"))
            {
                b = true;
            }
            else if( getTaskStatus.equals("nonCompletedTask"))
            {
                b = false;
            }

            preparedStatement.setBoolean(1,b);


            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    boolean status = rs.getBoolean("status");
                    String date = (String) rs.getString("date");
                    String priority = rs.getString("priority");

                    taskList.add(new Task(id,task,status,date,priority));
                }

                return taskList;

            }

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }

    public List<String> getTaskById(int id ) throws Exception {
        String qry = "SELECT task FROM task WHERE id = ?";

        List<String> taskList = new ArrayList<>();

        try ( Connection con = DBconnection.getDbConnection();
               PreparedStatement preparedStatement = con.prepareStatement(qry))
        {

            preparedStatement.setInt(1,id);

            try ( ResultSet rs = preparedStatement.executeQuery() ) {

                while (rs.next())
                {
                    taskList.add(rs.getString("task"));
                }

                return taskList;

            }

        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public List<Task> getByDate(String date) throws Exception {
        String qry = "SELECT * FROM task WHERE date = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setDate(1, Date.valueOf(date));

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    boolean status = rs.getBoolean("status");
                    String dates = (String) rs.getString("date");
                    String priority = rs.getString("priority");

                    taskList.add(new Task(id,task,status,dates,priority));
                }

                return taskList;

            }

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }

    public List<Task> getByPriorities(String priorities) throws Exception {
        String qry = "SELECT * FROM task WHERE priority = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setString(1, priorities);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    boolean status = rs.getBoolean("status");
                    String date = (String) rs.getString("date");
                    String priority = rs.getString("priority");

                    taskList.add(new Task(id,task,status,date,priority));
                }

                return taskList;

            }

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }

    public boolean setTask (String task,String priorities) throws Exception {
        String qry = "INSERT INTO task (task,priority) VALUES (?,?)";

        try(Connection con = DBconnection.getDbConnection();
             PreparedStatement preparedStatement = con.prepareStatement(qry)){

            preparedStatement.setString(1,task);
            preparedStatement.setString(2,priorities);

           int row = preparedStatement.executeUpdate();

           return row > 0;

        }
        catch (Exception e)
        {
            throw  e;
        }

    }

    public boolean isStatusUpdate(int id,String status ) throws Exception {
        String qry = "UPDATE task SET status = ? WHERE id = ? ";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            if ( "markAsRead".equals(status)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2,id);
            }
            else if ( "markAsUnRead".equals(status) )
            {
                preparedStatement.setBoolean(1,false);
                preparedStatement.setInt(2,id);

            }

             int row = preparedStatement.executeUpdate();

             return row > 0;

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }

    public boolean isTaskUpdate(int id,String newTask ) throws Exception {
        String qry = "UPDATE task SET task = ? WHERE id = ? ";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setString(1,newTask);
            preparedStatement.setInt(2,id);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }
        catch ( Exception e)
        {
            throw  e;

        }
    }

    public boolean isDelete( int id ) throws Exception {
        String qry = "DELETE from task WHERE id = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setInt(1,id);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }
        catch ( Exception e)
        {
            throw  e;

        }


    }













}
