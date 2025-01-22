# Task Tracker CLI

## Overview

Task Tracker CLI is a command-line application designed to help users manage their tasks effectively. With this app, you can add, update, delete, and track tasks, as well as filter them based on their status. Tasks are stored persistently in a JSON file located in the current directory.

This project focuses on enhancing programming skills, including working with the filesystem, handling user inputs, and designing a simple CLI interface.

---

## Features

- **Add Tasks**: Add new tasks with a unique identifier.
- **Update Tasks**: Modify existing task descriptions.
- **Delete Tasks**: Remove tasks from the list.
- **Task Status**: Mark tasks as `todo`, `in-progress`, or `done`.
- **List Tasks**:
    - View all tasks.
    - Filter tasks by status: `done`, `todo`, or `in-progress`.

---

## Task Properties

Each task has the following properties:

- **id**: A unique identifier for the task.
- **description**: A short description of the task.
- **status**: The current status of the task (`todo`, `in-progress`, `done`).
- **createdAt**: The date and time the task was created.
- **updatedAt**: The date and time the task was last updated.

---

## Usage

The application is run through the command line and accepts positional arguments for user inputs.

### Commands

#### Add a Task
```bash
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)
```

### Update a Task
```bash
task-cli update 1 "Buy groceries and go for a walk with the dog"
# Output: Task updated successfully
```

### Delete a Task
```bash
task-cli delete 1
# Output: Task deleted successfully
```

### Mark Task Status
```bash
task-cli mark-in-progress 1
# Output: Task mark in progress successfully

task-cli mark-done 1
# Output: Task mark as done successfully
```

### List Tasks
```bash
task-cli list 
# Output: Displays all tasks

task-cli list done 
# Output: Displays all tasks marked as done

task-cli list todo
# Output: Displays all tasks marked as todo

task-cli list in-progress
# Output: Displays all tasks marked as in-progress
```

## Running the Application
Ensure you have Java installed (version 8 or higher).

```bash
java -jar TaskTrackerCLI.jar
```

