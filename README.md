# Astronaut Daily Schedule Organizer

## Overview

The Astronaut Daily Schedule Organizer is a **console-based Java application** designed to help astronauts manage their daily tasks efficiently. The system allows astronauts to add, remove, view, and edit tasks while handling scheduling conflicts and priorities. It incorporates **OOP principles**, **SOLID design principles**, **design patterns**, and robust **error handling**.

---

## Features

### Mandatory Features

* **Add Task**: Add a task with description, start time, end time, priority level, category, and location.
* **Remove Task**: Remove an existing task.
* **View Tasks**: Display all tasks sorted by start time.
* **Conflict Detection**: Prevent adding tasks that overlap with existing ones.
* **Error Handling**: Provide clear messages for invalid inputs.

### Optional Features

* **Edit Task**: Modify any task attribute.
* **Mark Task Completed**: Mark a task as done.
* **View Tasks by Priority**: Filter tasks by priority levels.

### Logging

* **In-Memory Logging**: Tracks all actions and errors.
* Viewable at runtime using the "View Logs" menu option.

### Priority Levels

* `CRITICAL`, `HIGH`, `NORMAL`, `LOW`, `OPTIONAL`

### Task Categories & Locations

* Examples: `Exercise`, `Work`, `Research`, `Maintenance`.
* Locations: `Space Lab`, `Cockpit`, `Gym Module`, etc.

---

## SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**: Each class has a clear responsibility:

   * `Task` holds task details.
   * `ScheduleManager` manages task operations.
   * `Logger` handles logging.
   * `TimeUtils` handles time parsing and validation.

2. **Open/Closed Principle (OCP)**: Classes are open for extension but closed for modification:

   * New priority levels or categories can be added without modifying existing code.
   * Observer notifications can be extended without changing `ScheduleManager`.

3. **Liskov Substitution Principle (LSP)**: Any subclass or interface implementation (e.g., `ConflictObserver`) can replace the base type without affecting program correctness.

4. **Interface Segregation Principle (ISP)**: Interfaces like `ConflictObserver` are focused and only expose relevant methods.

5. **Dependency Inversion Principle (DIP)**: High-level modules (`ScheduleManager`) depend on abstractions (`ConflictObserver`) rather than concrete implementations (`ConflictNotifier`).

---

## Design Patterns Applied

1. **Singleton Pattern**: `ScheduleManager` ensures only one instance exists to manage all tasks.
2. **Factory Pattern**: `TaskFactory` validates and creates `Task` objects, ensuring consistency.
3. **Observer Pattern**: `ConflictNotifier` alerts registered observers when a task conflicts with existing tasks, decoupling conflict detection from user notifications.

---

## Directory Structure

```
src/
├── com/
│   └── space/
│       └── schedule/
│           ├── AstronautScheduleApp.java
│           ├── manager/
│           │   └── ScheduleManager.java
│           ├── model/
│           │   ├── Task.java
│           │   ├── PriorityLevel.java
│           │   └── TaskFactory.java
│           ├── observer/
│           │   ├── ConflictObserver.java
│           │   └── ConflictNotifier.java
│           └── util/
│               ├── Logger.java
│               └── TimeUtils.java
```

---

## Compilation and Running

### 1. Compile

Open terminal in `src` and run:

```powershell
javac com\space\schedule\*.java com\space\schedule\manager\*.java com\space\schedule\model\*.java com\space\schedule\observer\*.java com\space\schedule\util\*.java
```

### 2. Run

```powershell
java com.space.schedule.AstronautScheduleApp
```

### Notes

* All times must be entered in **HH:mm 24-hour format**.
* Single-digit hours are automatically normalized (e.g., `9:15` → `09:15`).
* Invalid times like `25:00` or `12:60` stop the operation and show an error without crashing.
* All data is **in-memory**; closing the application clears all tasks.

---

## Sample Menu

```
1. Add Task
2. View All Tasks
3. View Tasks by Priority
4. Edit Task
5. Remove Task
6. Mark Task as Completed
7. View Logs
8. Help
9. Exit
```

## Example Input/Output

```
Enter your choice: 1
Enter description: Morning Exercise
Enter start time (HH:mm): 07:00
Enter end time (HH:mm): 08:00
Enter priority: HIGH
Enter category: Exercise
Enter location: Gym Module
Task added successfully.

Enter your choice: 2
07:00 - 08:00: Morning Exercise [HIGH] - Category: Exercise - Location: Gym Module
```

---

## Error Handling Examples

* **Invalid time**: `25:00` → `ERROR: Invalid start time. Task not added.`
* **Conflict**: Task overlaps existing task → `WARNING: Task conflicts with existing schedule: ...`
* **Non-existent task removal** → `ERROR: Task not found.`

---

## Dependencies

* Java 8 or higher
* No external libraries required

---

## Author

Raja S.
