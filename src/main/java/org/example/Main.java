package org.example;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker CLI. Type 'task-cli --help' to see usage instructions.");

        Scanner scanner = new Scanner(System.in);
        String input;
        ActionManager actionManager = new ActionManager();

        while (true) {
            input = scanner.nextLine();

            Pattern pattern = Pattern.compile("(\"[^\"]*\"|\\S+)");
            Matcher matcher = pattern.matcher(input);
            String[] inputArray = matcher.results().map(matchResult -> matchResult.group(1)).toArray(String[]::new);

            if (inputArray.length < 2 || !inputArray[0].equals("task-cli")) {
                System.out.println("Usage: task-cli --help");
                continue;
            }

            String action = inputArray[1];
            int taskId;

            switch (action) {

                case "add":

                    if (inputArray.length < 3) {
                        System.out.println("Usage: task-cli add <task-name>");
                        break;
                    }
                    String taskName = inputArray[2].replaceAll("^\"|\"$", "");
                    System.out.println(actionManager.addTask(taskName));
                    break;

                case "delete":

                    if (inputArray.length < 3) {
                        System.out.println("Usage: task-cli delete <task-id>");
                        break;
                    }
                    taskId = Integer.parseInt(inputArray[2]);
                    System.out.println(actionManager.deleteTask(taskId));
                    break;

                case "update":

                    if (inputArray.length < 4) {
                        System.out.println("Usage: task-cli update <task-id> <new-task-name>");
                        break;
                    }

                    taskId = Integer.parseInt(inputArray[2]);
                    String newTaskName = inputArray[3].replaceAll("^\"|\"$", "");
                    System.out.println(actionManager.updateTask(taskId, newTaskName));
                    break;

                case "mark-done":

                    if (inputArray.length < 3) {
                        System.out.println("Usage: task-cli mark-in-progress <task-id>");
                        break;
                    }
                    taskId = Integer.parseInt(inputArray[2]);
                    System.out.println(actionManager.markTaskDone(taskId));
                    break;

                case "mark-in-progress":

                    if (inputArray.length < 3) {
                        System.out.println("Usage: task-cli mark-in-progress <task-id>");
                        break;
                    }

                    taskId = Integer.parseInt(inputArray[2]);
                    System.out.println(actionManager.markTaskInProgress(taskId));
                    break;

                case "list":

                    if (inputArray.length > 3) {
                        System.out.println("Usage: task-cli list OPTIONAL[<task-status>]");
                        break;
                    }

                    String taskStatus = inputArray.length < 3 ? "all" : inputArray[2];
                    if (isValidTaskStatus(taskStatus)) {
                        System.out.println(actionManager.listTasks(taskStatus));
                    } else {
                        System.out.println("Invalid task status");
                    }
                    break;

                default:
                    System.out.println("Usage: task-cli --help");
                    break;
            }
        }
    }


    public static boolean isValidTaskStatus(String taskStatus) {
        ActionManager actionManager = new ActionManager();
        String[] taskStatuses = actionManager.getAllowedTaskStatuses();
        for( String status : taskStatuses) {
            if(status.equals(taskStatus)) {
                return true;
            }
        }
        return false;
    }
}