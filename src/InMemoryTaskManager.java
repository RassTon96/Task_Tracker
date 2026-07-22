import exception.EpicNotFoundException;
import exception.SubtaskNotFoundException;
import exception.TaskNotFoundException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private static int id;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Subtask> subtasks;
    private final Map<Integer, Epic> epics;
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.id = 0;
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public void createTask(Task task) {
        task.setId(this.id);
        this.id++;
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(this.id);
        this.id++;
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(this.id);
        this.id++;
        epics.put(epic.getId(), epic);
    }

    @Override
    public void getListAllTasks() {

        if (tasks.isEmpty()) {
            System.out.println("Обычных задач нет!");
        } else {
            System.out.println("Все обычные задачи: ");
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        }

        if (subtasks.isEmpty()) {
            System.out.println("Подзадач нет!");
        } else {
            System.out.println("Все подзадачи: ");
            for (Subtask subtask : subtasks.values()) {
                System.out.println(subtask);
            }
        }

        if (epics.isEmpty()) {
            System.out.println("Эпиков нет!");
        } else {
            System.out.println("Все эпики: ");
            for (Epic epic : epics.values()) {
                System.out.println(epic);
            }
        }
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.addHistory(task);
            return task;
        }
        throw new TaskNotFoundException("Task with id " + id + " not found");
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.addHistory(subtask);
            return subtask;
        }
        throw new SubtaskNotFoundException("Subtask with id " + id + " not found");
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.addHistory(epic);
            return epic;
        }
        throw new EpicNotFoundException("Epic with id " + id + " not found");
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        epics.remove(id);
    }





    ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.epicId == epicId) result.add(subtask);
        }
        return result;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.id)) {
            tasks.put(task.id, task);
        }

    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.id)) {
            subtasks.put(subtask.id, subtask);
            updateEpicStatus(subtask.epicId);
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        ArrayList<Subtask> epicsSubtasks = getSubtasksOfEpic(epicId);
        if (epicsSubtasks.isEmpty()) {
            epic.status = Task.Status.NEW;
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicsSubtasks) {
            if (!Task.Status.NEW.equals(subtask.status)) allNew = false;
            if (!Task.Status.DONE.equals(subtask.status)) allDone = false;
        }

        if (allNew) epic.status = Task.Status.NEW;
        else if (allDone) epic.status = Task.Status.DONE;
        else epic.status = Task.Status.IN_PROGRESS;
    }
}