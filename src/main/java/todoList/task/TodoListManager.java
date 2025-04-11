package todoList.task;

import todoList.database.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoListManager {


    public static  final TodoListManager INSTANCE = new TodoListManager();

    private TodoListManager()
    {
    }




    public List<Task> getAllTask(int userId) throws Exception {

        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task WHERE userid = ?";


        try (Connection connection = DBconnection.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setInt(1,userId);

            try (ResultSet rs = preparedStatement.executeQuery()){

                List<Task> taskList = new ArrayList<>();

                while ( rs.next()) {

                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String date = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id,task,status,date,priority,due_date,description));

                }
                return taskList;
            }

        }
    }

    public List<String> getTagsByTaskId (int taskId) throws Exception {
        String qry = "SELECT tagName FROM task_tags WHERE taskId = ?";
        List<String > tagList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setInt(1,taskId);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    tagList.add(rs.getString("tagName"));
                }

                return tagList;

            }

        }

    }




    public List<Task> getByStatus(int taskStatus,int userId) throws Exception {
        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task  WHERE status = ? AND usersId = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(qry))
        {


            preparedStatement.setInt(1,taskStatus);
            preparedStatement.setInt(2,userId);


            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String date = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id,task,status,date,priority,due_date,description));
                }

                return taskList;

            }

        }

    }

    public List<String> getTaskById(int taskId,int userId) throws Exception {
        String qry = "SELECT task FROM task WHERE id = ? AND userId = ?";

        List<String> taskList = new ArrayList<>();

        try ( Connection con = DBconnection.getDbConnection();
               PreparedStatement preparedStatement = con.prepareStatement(qry))
        {

            preparedStatement.setInt(1,taskId);

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

    public List<Task> getByDate(String date,int userID) throws Exception {

        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task WHERE date = ? AND userid = ?";

        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2,userID);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String dates = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id,task,status,date,priority,due_date,description));
                }

                return taskList;

            }

        }

    }

    public List<Task> getByDueDate(String date,int userID) throws Exception {

        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task WHERE due_date = ? AND userid = ?";

        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2,userID);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String dates = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id,task,status,date,priority,due_date,description));
                }

                return taskList;

            }

        }

    }


    public List<Task> getByPriorities(String priorities,int userId) throws Exception {
        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task WHERE priority = ? AND userId = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setString(1, priorities);
            preparedStatement.setInt(2,userId);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String date = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id,task,status,date,priority,due_date,description));
                }

                return taskList;

            }

        }

    }

    public int getPendingCount(int userId) throws SQLException {
        String qry = "SELECT COUNT(*) AS total FROM task WHERE status = 0 AND userid = ?";

        try ( Connection con = DBconnection.getDbConnection() ;
              PreparedStatement preparedStatement = con.prepareStatement(qry))
        {

            preparedStatement.setInt(1,userId);

           try ( ResultSet rs = preparedStatement.executeQuery(); )
           {
              return rs.getInt("total");
           }

        }

    }

    public List<Task> getByTag(String tag,int userId) throws Exception {
        String qry = "SELECT id,task,status,date,priority,due_date,description FROM task JOIN task_tags ON task.id = task_tags.taskId WHERE task.userid = ? AND task_tags.tagName = ?";
        List<Task> taskList = new ArrayList<>();

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2,tag);

            try (ResultSet rs = preparedStatement.executeQuery()){

                while ( rs.next())
                {
                    int id = rs.getInt("id");
                    String task = rs.getString("task");
                    int status = rs.getInt("status");
                    String date = rs.getString("date");
                    String priority = rs.getString("priority");
                    String due_date = rs.getString("due_date");
                    String description = rs.getString("description");

                    taskList.add(new Task(id, task, status, date, priority, due_date, description));

                }

                return taskList;

            }

        }

    }



    public static int getUserIdByTaskId(int taskId) throws SQLException {
        String qry = "SELECT userid FROM task WHERE id = ?";

        try ( Connection con = DBconnection.getDbConnection() ;
              PreparedStatement preparedStatement = con.prepareStatement(qry))
        {

            preparedStatement.setInt(1,taskId);

            try ( ResultSet rs = preparedStatement.executeQuery(); )
            {
                return rs.getInt("id");
            }

        }

    }

    public static int getUserIdByTagId(int tagId) throws SQLException {

        String qry = "SELECT userid FROM task JOIN task_tags ON task.id = task_tags.taskId WHERE tagId = ?";

        try ( Connection con = DBconnection.getDbConnection() ;
              PreparedStatement preparedStatement = con.prepareStatement(qry))
        {

            preparedStatement.setInt(1,tagId);

            try ( ResultSet rs = preparedStatement.executeQuery(); )
            {
                return rs.getInt("tagId");
            }

        }

    }


    public boolean setTask (String task,int status,String priorities,int userId,String dueDate,String description) throws Exception {
        String qry = "INSERT INTO task (task,status,priority,userid,due_date,description) VALUES (?,?,?,?,?,?)";

        try(Connection con = DBconnection.getDbConnection();
             PreparedStatement preparedStatement = con.prepareStatement(qry)){

            preparedStatement.setString(1,task);
            preparedStatement.setInt(2,status);
            preparedStatement.setString(3,priorities);
            preparedStatement.setInt(4,userId);
            preparedStatement.setDate(5,Date.valueOf(dueDate));
            preparedStatement.setString(6,description);

           int row = preparedStatement.executeUpdate();

           return row > 0;

        }


    }

    public boolean setDudeDate(String dueDate,int taskId) throws Exception {
        String qry = "UPDATE task SET due_date = ? WHERE id = ?";


        try(Connection con = DBconnection.getDbConnection();
            PreparedStatement preparedStatement = con.prepareStatement(qry)){


            preparedStatement.setDate(1,Date.valueOf(dueDate));
            preparedStatement.setInt(2,taskId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }


    }

    public boolean setDescription(String description,int taskId) throws Exception {
        String qry = "UPDATE task SET description = ? WHERE id = ?";


        try(Connection con = DBconnection.getDbConnection();
            PreparedStatement preparedStatement = con.prepareStatement(qry)){


            preparedStatement.setString(1,description);
            preparedStatement.setInt(2,taskId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }


    }


    public boolean setTaskTag(int taskId,String tagName) throws Exception {
        String qry = "INSERT INTO task_tags (taskId,tagName) VALUES (?,?)";

        try(Connection con = DBconnection.getDbConnection();
            PreparedStatement preparedStatement = con.prepareStatement(qry)){



            preparedStatement.setInt(1,taskId);
            preparedStatement.setString(2,tagName);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }
        catch (Exception e)
        {
            throw  e;
        }

    }






    public boolean isTaskStatusUpdate(int id,int updateStatus,int userId ) throws Exception {
        String qry = "UPDATE task SET status = ? WHERE id = ? AND userid = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setInt(1,updateStatus);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,userId);

             int row = preparedStatement.executeUpdate();

             return row > 0;

        }

    }

    public boolean isTaskUpdate(int id,String newTask,int userId ) throws Exception {
        String qry = "UPDATE task SET task = ? WHERE id = ? AND userid = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setString(1,newTask);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,userId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }

    public boolean isDueDateUpdate(int id,String newDueDate,int userId) throws Exception {
        String qry = "UPDATE task SET due_date = ? WHERE id = ? AND  userid = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setDate(1,Date.valueOf(newDueDate));
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,userId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }

    public boolean isDescriptionUpdate(int id,String newDescription,int userId) throws Exception {
        String qry = "UPDATE task SET description = ? WHERE id = ? AND  userid = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setString(1,newDescription);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,userId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }

    public boolean isPriorityUpdate(int id,String priority,int userId) throws Exception {
        String qry = "UPDATE task SET priority = ? WHERE id = ? AND  userid = ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setString(1,priority);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,userId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }


    public boolean isTagUpdate(int tagId,String newTag) throws Exception {
        String qry = "UPDATE task_tags SET tagName = ? WHERE tagid= ?";

        try ( Connection connection = DBconnection.getDbConnection();
              PreparedStatement  preparedStatement = connection.prepareStatement(qry))
        {

            preparedStatement.setString(1,newTag);
            preparedStatement.setInt(2,tagId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }



    public boolean isTaskDeleted( int id ,int userId) throws Exception {
        String qry = "DELETE from task WHERE id = ? AND userid = ?";

        try (Connection connection = DBconnection.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(qry)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }

    public boolean isTagDeleted( int tagId ) throws Exception {
        String qry = "DELETE from task_tags WHERE tagid = ?";

        try (Connection connection = DBconnection.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(qry)) {

            preparedStatement.setInt(1, tagId);

            int row = preparedStatement.executeUpdate();

            return row > 0;

        }

    }















}
