package com.example.orchardmanagementsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private static TaskManager instance;
    // First key: cropName, Second key: dateKey
    private HashMap<String, HashMap<String, List<Task2>>> taskMap;

    public HashMap<String, HashMap<String, List<Task2>>> getTaskMap() {
        return taskMap;
    }
    public Map<String, List<Task2>> getTasksForCrop(String cropName) {
        HashMap<String, List<Task2>> cropTasks = taskMap.get(cropName);
        if (cropTasks != null) {
            return cropTasks;
        } else {
            return new HashMap<>();
        }
    }

    private TaskManager() {
        taskMap = new HashMap<>();
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public void addTask(String cropName, String dateKey, Task2 task) {
        HashMap<String, List<Task2>> cropTasks = taskMap.getOrDefault(cropName, new HashMap<>());
        List<Task2> taskList = cropTasks.getOrDefault(dateKey, new ArrayList<>());
        taskList.add(task);
        cropTasks.put(dateKey, taskList);
        taskMap.put(cropName, cropTasks);
    }

    public List<Task2> getTasksForDate(String cropName, String dateKey) {
        HashMap<String, List<Task2>> cropTasks = taskMap.get(cropName);
        if (cropTasks != null) {
            return cropTasks.getOrDefault(dateKey, new ArrayList<>());
        }
        return new ArrayList<>();
    }

    public void removeTask(String cropName, String dateKey, int position) {
        HashMap<String, List<Task2>> cropTasks = taskMap.get(cropName);
        if (cropTasks != null) {
            List<Task2> taskList = cropTasks.get(dateKey);
            if (taskList != null && position >= 0 && position < taskList.size()) {
                taskList.remove(position);
                if (taskList.isEmpty()) {
                    cropTasks.remove(dateKey);
                } else {
                    cropTasks.put(dateKey, taskList);
                }
            }
            if (cropTasks.isEmpty()) {
                taskMap.remove(cropName);
            } else {
                taskMap.put(cropName, cropTasks);
            }
        }
    }

    public void clearTasksForDate(String cropName, String dateKey) {
        HashMap<String, List<Task2>> cropTasks = taskMap.get(cropName);
        if (cropTasks != null) {
            cropTasks.remove(dateKey);
            if (cropTasks.isEmpty()) {
                taskMap.remove(cropName);
            } else {
                taskMap.put(cropName, cropTasks);
            }
        }
    }
    public void clearTasksForCrop(String cropName) {
        if (taskMap.containsKey(cropName)) {
            taskMap.remove(cropName);
        }
    }
}





