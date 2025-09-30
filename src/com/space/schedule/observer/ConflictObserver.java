package com.space.schedule.observer;


import com.space.schedule.model.Task;


public interface ConflictObserver {
void onConflict(Task task);
}