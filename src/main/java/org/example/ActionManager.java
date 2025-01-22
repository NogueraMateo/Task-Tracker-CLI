package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ActionManager {

    private final String[] allowedActions = new String[] {
            "add", "update", "delete", "mark-in-progress", "mark-done", "list", "--help"
    };


    public ActionManager() {
        createTasksFile();
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
    throws IOException, ClassNotFoundException
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
        JSONObject tasksJson = new JSONObject();

        // Get the existing json
        try {
            tasksJson = getTasksJson();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading tasks file.");
        }

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
        JSONObject tasksJson = new JSONObject();
        try {
            tasksJson = this.getTasksJson();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading tasks file.");
        }
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
        JSONObject tasksJson = new JSONObject();

        try {
            tasksJson = this.getTasksJson();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading tasks file.");
        }

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
        JSONObject tasksJson = new JSONObject();

        try {
            tasksJson = this.getTasksJson();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading tasks file.");
        }

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
        JSONObject tasksJson = new JSONObject();

        try {
            tasksJson = this.getTasksJson();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading tasks file.");
        }

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
}
