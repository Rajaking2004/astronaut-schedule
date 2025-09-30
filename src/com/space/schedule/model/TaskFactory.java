package com.space.schedule.model;


import com.space.schedule.util.TimeUtils;


public class TaskFactory {
public static Task createTask(String description, String start, String end, PriorityLevel priority, String category, String location) {
if (!TimeUtils.isValidTime(start) || !TimeUtils.isValidTime(end))
throw new IllegalArgumentException("Invalid time format.");
return new Task(description, start, end, priority, category, location);
}
}