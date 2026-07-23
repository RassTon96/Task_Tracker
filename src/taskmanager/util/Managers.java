package taskmanager.util;

import taskmanager.history.HistoryManager;
import taskmanager.history.InMemoryHistoryManager;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.manager.TaskManager;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}