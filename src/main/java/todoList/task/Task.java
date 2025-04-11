package todoList.task;

import java.util.List;

public class Task {


    private int id;

    private String task;

    private int status;

    private String date;

    private String priority;

    private String due_date;

    private String description;

    private List<String>  tags;



    public Task(int id, String task, int status, String date,String priority) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.date = date;
        this.priority = priority;
    }

    public Task(int id, String task, int status, String date, String priority, String due_date, String description) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.date = date;
        this.priority = priority;
        this.due_date = due_date;
        this.description = description;
    }

    public Task(int id, String task, int status, String date, String priority, String due_date, String description, List<String> tags) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.date = date;
        this.priority = priority;
        this.due_date = due_date;
        this.description = description;
        this.tags = tags;
    }

    public String getStatusText(int status) {
        switch (status) {
            case 1:
                return "Completed";
            case 2:
                return "Due";
            case 0:
                return "Finished";
            default:
                return "Unknown";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setPriority(String priority) {
        this.priority = priority;






    }
}
