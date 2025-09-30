package com.space.schedule.util;


import java.util.ArrayList;
import java.util.List;


public class Logger {
private static Logger instance;
private final List<String> logs = new ArrayList<>();


private Logger() {}


public static Logger getInstance() {
if (instance == null) instance = new Logger();
return instance;
}


public void info(String msg) { logs.add("[INFO] " + msg); }
public void error(String msg) { logs.add("[ERROR] " + msg); }
public void printLogs() { logs.forEach(System.out::println); }
}