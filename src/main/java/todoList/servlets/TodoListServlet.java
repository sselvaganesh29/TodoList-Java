package todoList.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import todoList.task.Task;
import todoList.task.TodoListManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TodoListServlet extends HttpServlet
{



    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        JSONObject jsonResponse = new JSONObject();

        String path = httpServletRequest.getServletPath();

        int status = Integer.parseInt(httpServletRequest.getParameter("TaskStatus"));

        String pendingCount = httpServletRequest.getParameter("Count");

        String due_date = httpServletRequest.getParameter("due_date");

        String date = httpServletRequest.getParameter("date");

        String priorities = httpServletRequest.getParameter("TaskLevel");

        JSONArray  jsonArray = new JSONArray();

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        int userId = (int) httpServletRequest.getAttribute("userId");

        String tag = httpServletRequest.getParameter("#tag");








        try {
            switch (path) {


                case "/getAllTask":
                    List<Task> allTaskList = todoListManager.getAllTask(userId);

                    for ( Task t : allTaskList)
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()) );
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());

                        JSONArray tagsJson = new JSONArray(todoListManager.getTagsByTaskId(t.getId()));

                        jsonObject.put("#Tags",tagsJson);


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);
                    break;

                case "/getByStatus":

                        List<Task> taskList = todoListManager.getByStatus(status,userId);

                    for ( Task t : taskList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()));
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());

                        JSONArray tagsJson = new JSONArray(todoListManager.getTagsByTaskId(t.getId()));

                        jsonObject.put("#Tags",tagsJson);


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                    break;

                case "/getByDate":

                    List<Task> dateList = todoListManager.getByDate(date,userId);

                    for ( Task t : dateList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()) );
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());

                        JSONArray tagsJson = new JSONArray(todoListManager.getTagsByTaskId(t.getId()));

                        jsonObject.put("#Tags",tagsJson);


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                    break;

                case "/getByPriorities":

                    List<Task> prioTaskList = todoListManager.getByPriorities(priorities,userId);

                    for ( Task t : prioTaskList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()) );
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());

                        JSONArray tagsJson = new JSONArray(todoListManager.getTagsByTaskId(t.getId()));

                        jsonObject.put("#Tags",tagsJson);


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                break;

                case "/getByDueDate":

                    List<Task> dueDateTaskList = todoListManager.getByDueDate(due_date,userId);

                    for ( Task t : dueDateTaskList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()) );
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());

                        JSONArray tagsJson = new JSONArray(todoListManager.getTagsByTaskId(t.getId()));

                        jsonObject.put("#Tags",tagsJson);


                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                    break;

                case "/getByTag":

                    List<Task> tagTaskList = todoListManager.getByTag(tag,userId);

                    for ( Task t : tagTaskList )
                    {
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("id", t.getId());
                        jsonObject.put("task", t.getTask());
                        jsonObject.put("status", t.getStatusText(t.getStatus()) );
                        jsonObject.put("date", t.getDate());
                        jsonObject.put("priority",t.getPriority());
                        jsonObject.put("due_date",t.getDue_date());
                        jsonObject.put("description",t.getDescription());




                        jsonArray.put(jsonObject);
                    }

                    jsonResponse.put("Tasks",jsonArray);

                    break;



                default:
                    jsonResponse.put("Message","Invalid Url!!!");
                    break;
            }
        }
         catch (Exception e)
        {
            jsonResponse.put("Message" ,"Exception Occurs" + e.getMessage());
        }


        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }

    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        String addTask = httpServletRequest.getParameter("addTask");

        String priorities = httpServletRequest.getParameter("TaskLevel");

        String due_date = httpServletRequest.getParameter("due_date");

        String description = httpServletRequest.getParameter("Description");

        int taskId = Integer.parseInt(httpServletRequest.getParameter("taskId"));

        String tagName = httpServletRequest.getParameter("tagName");

        int userId = (int) httpServletRequest.getAttribute("userId");



        TodoListManager todoListManager = TodoListManager.INSTANCE;

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");



        BufferedReader reader = httpServletRequest.getReader();

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String requestBody = sb.toString();

        JSONObject jsonBody = new JSONObject(requestBody);

        String _task = jsonBody.getString("task");
        int _status = jsonBody.getInt("status");
        String _due_date = jsonBody.getString("due_date");
        String _priority = jsonBody.getString("priority");
        String _description = jsonBody.getString("description");



        if ( _task == null ) {
            jsonResponse.put("message", "missing params!!!");
            httpServletResponse.getWriter().write(jsonResponse.toString());
            return;
        }


        try {
            switch (path)
            {
                case "/addTask":

                    if ( todoListManager.setTask(_task,_status,_priority,userId,_due_date,description))
                    {
                        jsonResponse.put("Message","Task added");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something Wrong !!!!!");
                    }
                    break;

                case "/addDueDate" :

                    if ( todoListManager.setDudeDate(due_date,taskId) )
                    {
                        jsonResponse.put("Message","Due date added successfully");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something went wrong!!!");
                    }

                    break;

                case "/addDescription" :

                    if ( todoListManager.setDescription(description,taskId) )
                    {
                        jsonResponse.put("Message","Description added successfully");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something went wrong!!!");
                    }

                    break;

                case "/addTag" :

                    if ( todoListManager.setTaskTag(taskId,tagName) )
                    {
                        jsonResponse.put("Message","#tag added successfully");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something went wrong!!!");
                    }

                    break;

                default:
                     jsonResponse.put("Message","Invalid Url!!!");

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


        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        int taskId = Integer.parseInt(httpServletRequest.getParameter("id"));

        String newTask = httpServletRequest.getParameter("newTask");

        int newStatus = Integer.parseInt(httpServletRequest.getParameter("status"));

        String newDescription = httpServletRequest.getParameter("Description");

        String newPriority = httpServletRequest.getParameter("newPriority");

        String newDueDate = httpServletRequest.getParameter("newDueDate");

        String newTag = httpServletRequest.getParameter("tag");


        TodoListManager todoListManager = TodoListManager.INSTANCE;

        int userId = (int) httpServletRequest.getAttribute("userId");







        try {
            switch (path) {
                case "/updateTask":

                    if (todoListManager.isTaskUpdate(taskId, newTask, userId)) {
                        jsonResponse.put("Message", "Task update successfully");
                    } else {
                        jsonResponse.put("Message", "Failed to update!!!");
                    }

                    break;

                case "/updateDueDate":

                    if (todoListManager.isDueDateUpdate(taskId, newDueDate, userId)) {
                        jsonResponse.put("Message", "Due date update successfully");
                    } else {
                        jsonResponse.put("Message", "Failed to update!!!");
                    }

                    break;

                case "/updateDescription":

                    if (todoListManager.isDescriptionUpdate(taskId, newDescription, userId)) {
                        jsonResponse.put("Message", "Due date update successfully");
                    } else {
                        jsonResponse.put("Message", "Failed to update!!!");

                    }
                        break;

                        case "/updateStatus":

                            if ( todoListManager.isTaskStatusUpdate(taskId,newStatus,userId))
                            {
                                jsonResponse.put("Message","Status update successfully");
                            }
                            else
                            {
                                jsonResponse.put("Message","Failed to update!!!");
                            }

                            break;

                        case "/updatePriority":

                            if ( todoListManager.isPriorityUpdate(taskId,newTask,userId))
                            {
                                jsonResponse.put("Message","priority update successfully");
                            }
                            else
                            {
                                jsonResponse.put("Message","Failed to update!!!");
                            }

                            break;

                        case "/updateTag":

                            if ( todoListManager.isTagUpdate(taskId,newTag))
                            {
                                jsonResponse.put("Message","Priority update successfully");
                            }
                            else
                            {
                                jsonResponse.put("Message","Failed to update!!!");
                            }

                            break;

                        default:
                            jsonResponse.put("Message","Invalid Url!!!");
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


        String path = httpServletRequest.getServletPath();

        JSONObject jsonResponse = new JSONObject();

        int taskId = Integer.parseInt(httpServletRequest.getParameter("TaskId"));

        TodoListManager todoListManager = TodoListManager.INSTANCE;

        int userId = (int) httpServletRequest.getAttribute("userId");

        int tagId = Integer.parseInt(httpServletRequest.getParameter("tagId"));

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");


        try {
            switch (path)
            {
                case "/deleteTask":

                    if ( todoListManager.isTaskDeleted(taskId,userId))
                    {
                        jsonResponse.put("Message","Task Deleted");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something Wrong !!!!!");
                    }

                    break;

                case "/tagTask":

                    if ( todoListManager.isTagDeleted(tagId))
                    {
                        jsonResponse.put("Message","Tag Deleted");
                    }
                    else
                    {
                        jsonResponse.put("Message","Something Wrong !!!!!");
                    }

                    break;

                default:

                    jsonResponse.put("Message","Invalid Url!!!");

            }
        }
        catch (Exception e)
        {
            jsonResponse.put("Error",e.getMessage());
        }

        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();
    }








}
