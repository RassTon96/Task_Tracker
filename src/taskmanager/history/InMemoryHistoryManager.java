package taskmanager.history;

import taskmanager.tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> historyTasks;

    public InMemoryHistoryManager() {
        this.historyTasks = new LinkedList<>();
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }

    public void addHistory(Task task) {
        if (historyTasks.size() < 10) {
            historyTasks.add(task);
        } else {
            historyTasks.removeFirst();
            historyTasks.add(task);
        }
    }
}