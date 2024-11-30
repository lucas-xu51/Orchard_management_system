package com.example.orchardmanagementsystem;

import java.util.HashMap;
import java.util.List;

public class EventManager {

    private static EventManager instance;
    private HashMap<String, HashMap<String, List<Irrigation_addNewItem.Event>>> eventsMap;

    private EventManager() {
        eventsMap = new HashMap<>();
    }

    public static synchronized EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public HashMap<String, HashMap<String, List<Irrigation_addNewItem.Event>>> getEventsMap() {
        return eventsMap;
    }

    public void clearEventsForCrop(String cropName) {
        if (eventsMap.containsKey(cropName)) {
            eventsMap.remove(cropName);
        }
    }
}

