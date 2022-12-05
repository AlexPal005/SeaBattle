package com.example.seabattle.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateManager {
    private Map<String , List<Updater>> listeners = new HashMap<>();
    public UpdateManager(String... operations){
        for(String operation: operations){
            this.listeners.put(operation,new ArrayList<>());
        }
    }
    public void startUpdate(String eventType){
        List<Updater> users = listeners.get(eventType);
        for(Updater listener : users){
            listener.update(eventType);

        }
    }
    public void newUpdate(String eventType, Updater listener){
        List<Updater> users = listeners.get(eventType);
        users.add(listener);
    }
}

