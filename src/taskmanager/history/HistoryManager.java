package taskmanager.history;

import taskmanager.tasks.Task;

import java.util.List;

public interface HistoryManager {
    void addHistory(Task task);

    List<Task> getHistory();
}
