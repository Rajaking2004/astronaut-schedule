package com.space.schedule;

import java.util.Scanner;
import com.space.schedule.manager.ScheduleManager;

public class AstronautScheduleApp {
    private boolean isRunning = true;
    private final ScheduleManager manager = ScheduleManager.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new AstronautScheduleApp().start();
    }

    private void start() {
        while (isRunning) {
            showMenu();
            int choice = getUserChoice();
            handleChoice(choice);
        }
        System.out.println("Exiting... Goodbye, astronaut!");
    }

    private void showMenu() {
        System.out.println("\n================= Astronaut Schedule Organizer =================");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. View Tasks by Priority");
        System.out.println("4. Edit Task");
        System.out.println("5. Remove Task");
        System.out.println("6. Mark Task as Completed");
        System.out.println("7. View Logs");
        System.out.println("8. Help");
        System.out.println("9. Exit");
        System.out.print("===============================================================\nEnter your choice: ");
    }

    private int getUserChoice() {
        try { return Integer.parseInt(scanner.nextLine().trim()); } 
        catch (NumberFormatException e) { System.out.println("Invalid input. Please enter a number."); return -1; }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> manager.addTask(scanner);
            case 2 -> manager.viewTasks();
            case 3 -> manager.viewByPriority(scanner);
            case 4 -> manager.editTask(scanner);
            case 5 -> manager.removeTask(scanner);
            case 6 -> manager.markCompleted(scanner);
            case 7 -> manager.showLogs();
            case 8 -> showHelp();
            case 9 -> isRunning = false;
            default -> System.out.println("Unknown option. Please try again.");
        }
    }

    private void showHelp() {
        System.out.println("Help Menu:\n- Add, edit, and manage tasks with time validation.\n"
                         + "- Detects scheduling conflicts.\n"
                         + "- Supports categories, priorities, logs, and completion tracking.");
    }
}
