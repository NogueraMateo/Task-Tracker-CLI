package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ActionManager {

    private final String[] allowedActions = new String[] {
            "add", "update", "delete", "mark-in-progress", "mark-done", "list", "--help"
    };

    private final String[] allowedTaskStatuses = new String[] {
            "todo", "in-progress", "done"
    };


    public ActionManager() {
        createTasksFile();
    }


    public String[] getAllowedTaskStatuses() {
        return allowedTaskStatuses;
    }


    public void createTasksFile() {

        // Retrieve the file
        File file =  new File("./src/main/resources/data/tasks.json");

        // If the file doesn't exist, create it
        if (!file.exists()) {
            try {
                file.createNewFile();
                JSONObject tasksJson = new JSONObject();
                JSONArray tasks = new JSONArray();
                tasksJson.put("tasks", tasks);
                saveTasksJson(tasksJson);
            } catch (IOException e) {
                System.out.println("Error creating tasks file.");
            }
        }
    }


    public JSONObject getTasksJson()
    {
        // Retrieve the file
        File file =  new File("./src/main/resources/data/tasks.json");
        JSONObject tasksJson = new JSONObject();

        // Read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            StringBuilder jsonText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
            tasksJson = new JSONObject(jsonText.toString());

        } catch (IOException e) {
            System.out.println("Error reading tasks file.");
        }

        return tasksJson;
    }


    public void saveTasksJson(JSONObject tasksJson) {
        File file =  new File("./src/main/resources/data/tasks.json");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(tasksJson.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error saving tasks file." + e.getMessage());
        }
    }


    public String addTask(String task) {

        // Get the existing json
        JSONObject tasksJson = getTasksJson();

        // Get the array of tasks if there is any
        JSONArray tasks = tasksJson.getJSONArray("tasks");

        // Create the new task
        JSONObject newTask = new JSONObject();
        newTask.put("name", task);
        newTask.put("status", "todo");
        if (tasks.isEmpty()) {
            newTask.put("id", 1);
        } else {
            newTask.put("id", tasks.getJSONObject(tasks.length() - 1).getInt("id") + 1);
        }
        tasks.put(newTask);

        // Save the file
        saveTasksJson(tasksJson);
        return "New task added successfully.";
    }


    public String deleteTask(int taskId) {

        // Get the existing json
        JSONObject tasksJson = getTasksJson();

        JSONArray tasks = tasksJson.getJSONArray("tasks");
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getInt("id") == taskId) {
                tasks.remove(i);
                saveTasksJson(tasksJson);
                return "Task deleted successfully.";
            }
        }
        return "Task not found.";
    }


    public String updateTask(int taskId, String newTaskName) {

        // Get the existing json
        JSONObject tasksJson = getTasksJson();

        JSONArray tasks = tasksJson.getJSONArray("tasks");
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getInt("id") == taskId) {
                tasks.getJSONObject(i).put("name", newTaskName);
                saveTasksJson(tasksJson);
                return "Task updated successfully.";
            }
        }
        return "Task not found.";
    }


    public String markTaskDone(int taskId) {

        // Get the existing json
        JSONObject tasksJson = getTasksJson();

        JSONArray tasks = tasksJson.getJSONArray("tasks");
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getInt("id") == taskId) {
                tasks.getJSONObject(i).put("status", "done");
                saveTasksJson(tasksJson);
                return "Task marked as done successfully.";
            }
        }
        return "Task not found.";
    }


    public String markTaskInProgress(int taskId) {

        // Get the existing json
        JSONObject tasksJson = getTasksJson();

        JSONArray tasks = tasksJson.getJSONArray("tasks");
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getInt("id") == taskId) {
                tasks.getJSONObject(i).put("status", "in-progress");
                saveTasksJson(tasksJson);
                return "Task marked as in-progress successfully.";
            }
        }
        return "Task not found.";
    }


    public String listTasks(String taskStatus) {

        // Get the tasks json
        JSONObject tasksJson = getTasksJson();

        JSONObject task;
        JSONArray tasks = tasksJson.getJSONArray("tasks");
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < tasks.length(); i++) {
            task = tasks.getJSONObject(i);
            if (task.getString("status").equals(taskStatus)) {
                output.append(task.getString("name") +  " --- " + task.getString("status") + "\n");
            }
        }
        return output.toString();
    }
}
