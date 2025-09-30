package com.space.schedule.manager;

import java.util.*;
import java.util.stream.Collectors;

import com.space.schedule.model.*;
import com.space.schedule.observer.*;
import com.space.schedule.util.*;

public class ScheduleManager {
    private static ScheduleManager instance;
    private final List<Task> tasks = new ArrayList<>();
    private final List<ConflictObserver> observers = new ArrayList<>();
    private final Logger logger = Logger.getInstance();

    private ScheduleManager() {
        observers.add(new ConflictNotifier());
    }

    public static ScheduleManager getInstance() {
        if (instance == null) instance = new ScheduleManager();
        return instance;
    }

    public void addTask(Scanner scanner) {
        try {
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
            System.out.print("Enter start time (HH:mm): ");
            String start = scanner.nextLine();
            if (!TimeUtils.isValidTime(start)) {
                System.out.println("ERROR: Invalid start time. Task not added.");
                logger.error("Invalid start time: " + start);
                return;
            }
            System.out.print("Enter end time (HH:mm): ");
            String end = scanner.nextLine();
            if (!TimeUtils.isValidTime(start)) {
                System.out.println("ERROR: Invalid start time. Task not added.");
                logger.error("Invalid start time: " + start);
                return;
            }
            System.out.print("Enter priority (CRITICAL/HIGH/NORMAL/LOW/OPTIONAL): ");
            PriorityLevel priority = PriorityLevel.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            System.out.print("Enter location: ");
            String location = scanner.nextLine();

            Task newTask = TaskFactory.createTask(description, start, end, priority, category, location);

            if (hasConflict(newTask)) {
                notifyConflict(newTask);
                logger.error("Task conflicts with existing task.");
                return;
            }

            tasks.add(newTask);
            tasks.sort(Comparator.comparing(Task::getStartTime));
            logger.info("Task added: " + description);
            System.out.println("Task added successfully.");
        } catch (Exception e) {
            logger.error("Failed to add task: " + e.getMessage());
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    public void viewByPriority(Scanner scanner) {
        System.out.print("Enter priority to filter: ");
        try {
            PriorityLevel priority = PriorityLevel.valueOf(scanner.nextLine().toUpperCase());
            List<Task> filtered = tasks.stream()
                    .filter(t -> t.getPriority() == priority)
                    .collect(Collectors.toList());
            if (filtered.isEmpty()) System.out.println("No tasks with priority " + priority);
            else filtered.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Invalid priority.");
        }
    }

    public void editTask(Scanner scanner) {
        System.out.print("Enter task description to edit: ");
        String desc = scanner.nextLine();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getDescription().equalsIgnoreCase(desc)).findFirst();
        if (taskOpt.isEmpty()) { System.out.println("Task not found."); return; }
        Task task = taskOpt.get();

        System.out.print("Enter new description (leave blank to keep): ");
        String newDesc = scanner.nextLine();
        if (!newDesc.isEmpty()) task.setDescription(newDesc);

        System.out.print("Enter new start time (HH:mm, leave blank to keep): ");
        String newStart = scanner.nextLine();
        if (!newStart.isEmpty()) {
            if (!TimeUtils.isValidTime(newStart)) {
                System.out.println("ERROR: Invalid start time. Edit aborted.");
                logger.error("Invalid start time during edit: " + newStart);
                return;
            }
            task.setStartTime(TimeUtils.parseTime(newStart));
        }

        System.out.print("Enter new end time (HH:mm, leave blank to keep): ");
        String newEnd = scanner.nextLine();
        if (!newEnd.isEmpty()) {
            if (!TimeUtils.isValidTime(newEnd)) {
                System.out.println("ERROR: Invalid end time. Edit aborted.");
                logger.error("Invalid end time during edit: " + newEnd);
                return; 
                }
                task.setEndTime(TimeUtils.parseTime(newEnd));
        }
        System.out.print("Enter new priority (leave blank to keep): ");
        String newPriority = scanner.nextLine();
        if (!newPriority.isEmpty()) task.setPriority(PriorityLevel.valueOf(newPriority.toUpperCase()));

        System.out.print("Enter new category (leave blank to keep): ");
        String newCategory = scanner.nextLine();
        if (!newCategory.isEmpty()) task.setCategory(newCategory);

        System.out.print("Enter new location (leave blank to keep): ");
        String newLocation = scanner.nextLine();
        if (!newLocation.isEmpty()) task.setLocation(newLocation);

        logger.info("Task edited: " + desc);
        System.out.println("Task updated successfully.");
    }

    public void removeTask(Scanner scanner) {
        System.out.print("Enter task description to remove: ");
        String desc = scanner.nextLine();
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getDescription().equalsIgnoreCase(desc)).findFirst();
        if (taskOpt.isEmpty()) {
            logger.error("Task not found: " + desc);
            System.out.println("ERROR: Task not found.");
        } else {
            tasks.remove(taskOpt.get());
            logger.info("Task removed: " + desc);
            System.out.println("Task removed successfully.");
        }
    }

    public void markCompleted(Scanner scanner) {
        System.out.print("Enter task description to mark completed: ");
        String desc = scanner.nextLine();
        tasks.stream().filter(t -> t.getDescription().equalsIgnoreCase(desc))
             .findFirst()
             .ifPresentOrElse(task -> { task.setCompleted(true); logger.info("Task completed: " + desc); System.out.println("Task marked as completed."); },
                              () -> System.out.println("Task not found."));
    }

    private boolean hasConflict(Task newTask) {
        return tasks.stream().anyMatch(t -> !(t.getEndTime().isBefore(newTask.getStartTime()) ||
                                             t.getStartTime().isAfter(newTask.getEndTime())));
    }

    private void notifyConflict(Task task) { observers.forEach(o -> o.onConflict(task)); }

    public void showLogs() { logger.printLogs(); }
}
