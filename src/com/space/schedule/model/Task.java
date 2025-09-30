package com.space.schedule.model;


import java.time.LocalTime;
import com.space.schedule.util.TimeUtils;


public class Task {
private String description;
private LocalTime startTime;
private LocalTime endTime;
private PriorityLevel priority;
private String category;
private String location;
private boolean isCompleted;


public Task(String description, String start, String end, PriorityLevel priority, String category, String location) {
this.description = description;
this.startTime = TimeUtils.parseTime(start);
this.endTime = TimeUtils.parseTime(end);
this.priority = priority;
this.category = category;
this.location = location;
this.isCompleted = false;
}


// Getters and Setters
public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }
public LocalTime getStartTime() { return startTime; }
public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
public LocalTime getEndTime() { return endTime; }
public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
public PriorityLevel getPriority() { return priority; }
public void setPriority(PriorityLevel priority) { this.priority = priority; }
public String getCategory() { return category; }
public void setCategory(String category) { this.category = category; }
public String getLocation() { return location; }
public void setLocation(String location) { this.location = location; }
public boolean isCompleted() { return isCompleted; }
public void setCompleted(boolean completed) { isCompleted = completed; }


@Override
public String toString() {
return String.format("%s - %s: %s [%s] - Category: %s - Location: %s%s",
startTime, endTime, description, priority, category, location,
isCompleted ? " (Completed)" : "");
}
}