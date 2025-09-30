package com.space.schedule.observer;


import com.space.schedule.model.Task;


public class ConflictNotifier implements ConflictObserver {
@Override
public void onConflict(Task task) {
System.out.println("WARNING: Task conflicts with existing schedule: " + task.getDescription());
}
}