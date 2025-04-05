package todoList;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.util.List;

public class TodoListServlet extends HttpServlet
{


    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }



    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        JSONObject jsonResponse = new JSONObject();

        String path = httpServletRequest.getServletPath();

        String params = httpServletRequest.getParameter("CompletedOrNonCompleted");

        String pendingCount = httpServletRequest.getParameter("pendingCount");

        String date = httpServletRequest.getParameter("date");

        String priorities = httpServletRequest.getParameter("TaskLevel");

        JSONArray  jsonArray = new JSONArray();

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String SEC_KEY = "iAmIronMan";

        SecretKey SECRET_KEY = new SecretKeySpec(SEC_KEY.getBytes(), "AES");





        try {

            String token = EncryptDecrypt.decrypt((httpServletRequest.getHeader("encryptedToken")),SECRET_KEY);

            int id = UserManager.getIdByUserName(JSONtoken.getUsername(token));

            switch (path) {


                case "/getAllTask":
                    List<Task> allTaskList = todoListManager.getAllTask();

                    for ( Task t : allTaskList)
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.isStatus());
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);
                    break;

                case "/getCompletedOrNonCompletedTask":


                    if ( "completedTask".equals(params) ) {
                        List<Task> comList =  todoListManager.getCompletedTaskOrNonCompletedTask(params);

                        for (Task t : comList) {
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("id", t.getId());
                            jsonObject.put("task", t.getTask());
                            jsonObject.put("status", t.isStatus());
                            jsonObject.put("date", t.getDate());
                            jsonObject.put("priority",t.getPriority());


                            jsonArray.put(jsonObject);
                        }
                    }
                    else if ( "nonCompletedTask".equals(params) && pendingCount == null)
                    {
                        List<Task> nonComList = todoListManager.getCompletedTaskOrNonCompletedTask(params);

                        for ( Task t : nonComList)
                        {
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put("id", t.getId());
                            jsonObject.put("task", t.getTask());
                            jsonObject.put("status", t.isStatus());
                            jsonObject.put("date", t.getDate());
                            jsonObject.put("priority",t.getPriority());


                            jsonArray.put(jsonObject);
                        }
                    }
                    else if ( "pendingCount".equals(pendingCount) && params != null)
                    {
                        List<Task> nonComList = todoListManager.getCompletedTaskOrNonCompletedTask(params);

                        jsonResponse.put("pendingCount",nonComList.size());
                    }

                    jsonResponse.put("Tasks",jsonArray);
                    break;

                case "/getByDate":

                    List<Task> dateTask = todoListManager.getByDate(date);

                    for ( Task t : dateTask )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.isStatus());
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());


                        jsonArray.put(jsonObject);
                    }

                        jsonResponse.put("Tasks",jsonArray);

                    break;

                case "/getByPriorities":

                    List<Task> prioTaskList = todoListManager.getByPriorities(priorities);

                    for ( Task t : prioTaskList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.isStatus());
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                break;

                default:
                    jsonResponse.put("Message","Invalid Url !!!");
                    break;
            }
        }

        catch (ExpiredJwtException e)
        {
            jsonResponse.put("error", "Token has expired");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        }
        catch (Exception e)
        {
            jsonResponse.put("Message" ,"Exception Occurs" + e.getMessage());
        }


        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");



        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        String addTask = httpServletRequest.getParameter("task");

        String priorities = httpServletRequest.getParameter("TaskLevel");

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");


        if ( addTask == null && priorities == null ) {
            jsonResponse.put("message", "missing params!!!");
            httpServletResponse.getWriter().write(jsonResponse.toString());
            return;
        }


        try {
            switch (path)
            {
                case "/addTask":

                    if ( todoListManager.setTask(addTask,priorities))
                    {
                        jsonResponse.put("Message","Task added");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something Wrong !!!!!");
                    }

                    break;
            }
        }
        catch (Exception e)
        {
                jsonResponse.put("Message",e.getMessage());
        }


        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }

    public void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        int id = Integer.parseInt(httpServletRequest.getParameter("id"));

        String newTask = httpServletRequest.getParameter("newTask");

        String status = httpServletRequest.getParameter("status");

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");



        try {
            switch (path)
            {
                case "/updateTask":

                    if ( "markAsRead".equals(status)) {
                        if (todoListManager.isStatusUpdate(id, status)) {
                            jsonResponse.put("Message", "Task mark us read");
                        } else {
                            jsonResponse.put("Message", "Something Wrong !!!!!");
                        }
                    }
                    else if ( "markAsUnRead".equals(status) )
                    {
                        if (todoListManager.isStatusUpdate(id, status)) {
                            jsonResponse.put("Message", "Task mark us Unread");
                        } else {
                            jsonResponse.put("Message", "Something Wrong !!!!!");
                        }
                    } else if ( newTask != null )
                    {

                        if (todoListManager.isTaskUpdate(id,newTask))
                        {
                            jsonResponse.put("Message","Task Edit successfully");
                        }
                        else {
                            jsonResponse.put("Message", "Something Wrong !!!!!");
                        }

                    }
                break;
            }
        }
        catch (Exception e)
        {
            jsonResponse.put("Message",e.getMessage());
        }


        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }

    public void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        int id = Integer.parseInt(httpServletRequest.getParameter("id"));

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");


        try {
            switch (path)
            {
                case "/deleteTask":

                    if ( todoListManager.isDelete(id))
                    {
                        jsonResponse.put("Message","Task Deleted");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something Wrong !!!!!");
                    }

                    break;
            }
        }
        catch (Exception e)
        {
            jsonResponse.put("Message",e.getMessage());
        }

        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }








}
