import exception.EpicNotFoundException;
import exception.SubtaskNotFoundException;
import exception.TaskNotFoundException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    int id = 0;

    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, Subtask> subtasks = new HashMap<>();
    Map<Integer, Epic> epics = new HashMap<>();

    List<Task> historyTasks = new ArrayList<>();

    @Override
    public void createTask(Task task) {
        task.id = this.id;
        this.id++;
        tasks.put(task.id, task);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.id = this.id;
        this.id++;
        subtasks.put(subtask.id, subtask);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.id = this.id;
        this.id++;
        epics.put(epic.id, epic);
    }

    @Override
    public void getListAllTasks() {

        if (tasks.isEmpty()) {
            System.out.println("Обычных задач нет!");
        } else {
            System.out.println("Все обычные задачи: ");
            for (Task task : tasks.values()) {
                System.out.println("ID: " + task.id + " Имя: " + task.name + " Статус: " + task.status);
            }
        }

        if (subtasks.isEmpty()) {
            System.out.println("Подзадач нет!");
        } else {
            System.out.println("Все подзадачи: ");
            for (Subtask subtask : subtasks.values()) {
                System.out.println("ID: " + subtask.id + " Имя: " + subtask.name + " Статус: " + subtask.status);
            }
        }

        if (epics.isEmpty()) {
            System.out.println("Эпиков нет!");
        } else {
            System.out.println("Все эпики: ");
            for (Epic epic : epics.values()) {
                System.out.println("ID: " + epic.id + " Имя: " + epic.name + " Статус: " + epic.status);
            }
        }
        System.out.println();
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.get(id) != null) {
            addHistory(tasks.get(id));
            return tasks.get(id);
        }
        throw new TaskNotFoundException("Task with id " + id + " not found");
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.get(id) != null) {
            addHistory(subtasks.get(id));
            return subtasks.get(id);
        }
        throw new SubtaskNotFoundException("Subtask with id " + id + " not found");
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.get(id) != null) {
            addHistory(epics.get(id));
            return epics.get(id);
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

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }

    @Override
    public void addHistory(Task task) {
        if (historyTasks.size() != 10) {
            historyTasks.add(task);
        } else {
            historyTasks.removeFirst();
            historyTasks.add(task);
        }
    }

    ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.epicId == epicId) result.add(subtask);
        }
        return result;
    }

    void updateTask(Task task) {
        if (tasks.containsKey(task.id)) {
            tasks.put(task.id, task);
        }

    }

    void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.id)) {
            subtasks.put(subtask.id, subtask);
            updateEpicStatus(subtask.epicId);
        }
    }

    void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        ArrayList<Subtask> epicsSubtasks = getSubtasksOfEpic(epicId);
        if (epicsSubtasks.isEmpty()) {
            epic.status = "NEW";
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicsSubtasks) {
            if (!subtask.status.equals("NEW")) allNew = false;
            if (!subtask.status.equals("DONE")) allDone = false;
        }

        if (allNew) epic.status = "NEW";
        else if (allDone) epic.status = "DONE";
        else epic.status = "IN_PROGRESS";
    }
}