import exceptions.EpicNotFoundException;
import exceptions.SubtaskNotFoundException;
import exceptions.TaskNotFoundException;

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
        task.setId(this.id++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void createSubtask(Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            subtask.setEpicId(epicId);
            subtask.setId(this.id++);
            subtasks.put(subtask.getId(), subtask);
            updateEpicStatus(epicId);
        } else {
            System.out.println("При добавлении подзадачи в эпик с ID " + epicId + ". Эпик не найден!");
        }
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(this.id++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public List<Task> getListAllTasks() {
        List<Task> listAllTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            listAllTasks.add(task);
        }
        return listAllTasks;
    }

    @Override
    public List<Subtask> getListAllSubtasks() {
        List<Subtask> listAllSubtasks = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            listAllSubtasks.add(subtask);
        }
        return listAllSubtasks;
    }

    @Override
    public List<Epic> getListAllEpics() {
        List<Epic> listAllEpics = new ArrayList<>();
        for (Epic epic : epics.values()) {
            listAllEpics.add(epic);
        }
        return listAllEpics;
    }

    @Override
    public List<Subtask> getSubtasksOfEpic(int epicId) {
        List<Subtask> listSubtasksOfEpic = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                listSubtasksOfEpic.add(subtask);
            }
        }
        return listSubtasksOfEpic;
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
        if (tasks.isEmpty()) {
            System.out.println("Обычных задач не найдено!");
        } else {
            System.out.println("Обычных задач было удаленно: " + tasks.size());
            tasks.clear();
        }
    }

    @Override
    public void deleteAllSubtasks() {
        if (subtasks.isEmpty()) {
            System.out.println("Подзадач не найдено");
        } else {
            System.out.println("Подзадач было удалено: " + subtasks.size());
            subtasks.clear();
            for (Epic epic : epics.values()) {
                updateEpicStatus(epic.getId());
            }
        }
    }

    @Override
    public void deleteAllEpic() {
        if (epics.isEmpty()) {
            System.out.println("Эпиков не найдено!");
        } else {
            System.out.println("Эпиков было удалено: " + epics.size());
            epics.clear();
        }
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Обычная задача с ID " + id + " удалена!");
        } else {
            System.out.println("При удалении обычная задача с ID " + id + " не найдена!");
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            subtasks.remove(id);
            System.out.println("Подзадача с ID " + id + " удалена!");
            updateEpicStatus(subtask.getEpicId());
        } else {
            System.out.println("При удалении подзадача с ID " + id + " не найдена!");
        }
    }

    @Override
    public void deleteEpicById(int id) {
        List<Integer> idsToRemove = new ArrayList<>();
        if (epics.containsKey(id)) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicId() == id) {
                    idsToRemove.add(subtask.getId());
                }
            }
            for (Integer subId : idsToRemove) {
                subtasks.remove(subId);
            }
            epics.remove(id);
            System.out.println("Эпик с ID " + id + " удалён!");
        } else {
            System.out.println("При удалении эпик с ID " + id + " не найден!");
        }
    }

    @Override
    public void updateTask(Task task) {
        int taskId = task.getId();
        if (tasks.containsKey(taskId)) {
            tasks.put(taskId, task);
            System.out.println("Обычная задача с ID " + taskId + " обновлена!");
        } else {
            System.out.println("При обновлении обычная задача с ID " + taskId + " не найдена!");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        if (subtasks.containsKey(subtaskId)) {
            subtasks.put(subtaskId, subtask);
            updateEpicStatus(subtask.getEpicId());
            System.out.println("Подзадача с ID " + subtaskId + " обновлена!");
        } else {
            System.out.println("При обновлении подзадача с ID " + subtaskId + " не найдена!");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        int epicId = epic.getId();
        if (epics.containsKey(epicId)) {
            Epic currentEpic = epics.get(epicId);
            currentEpic.setName(epic.getName());
            currentEpic.setDescription(epic.getDescription());
            updateEpicStatus(epicId);
            System.out.println("Эпик с ID " + epicId + " обновлён!");
        } else {
            System.out.println("При обновлении эпик с ID " + epicId + " не найден!");
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        List<Subtask> epicsSubtasks = getSubtasksOfEpic(epicId);
        if (epicsSubtasks.isEmpty()) {
            epic.setStatus(Task.Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicsSubtasks) {
            if (!Task.Status.NEW.equals(subtask.getStatus())) allNew = false;
            if (!Task.Status.DONE.equals(subtask.getStatus())) allDone = false;
        }

        if (allNew) epic.setStatus(Task.Status.NEW);
        else if (allDone) epic.setStatus(Task.Status.DONE);
        else epic.setStatus(Task.Status.IN_PROGRESS);
    }
}