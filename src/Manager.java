import java.util.HashMap;
import java.util.ArrayList;

public class Manager {
    int id = 0;

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    void createTask(Task task) {
        task.id = this.id;
        this.id++;
        tasks.put(task.id, task);
    }

    void createSubtask(Subtask subtask) {
        subtask.id = this.id;
        this.id++;
        subtasks.put(subtask.id, subtask);
    }

    void createEpic(Epic epic) {
        epic.id = this.id;
        this.id++;
        epics.put(epic.id, epic);
    }

    void getListAllTasks() {

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
    }

    Task getTaskById(int id) {
        return tasks.get(id);
    }

    Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    Epic getEpicById(int id) {
        return epics.get(id);
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

    void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
    }

    void deleteTaskById(int id) {
        tasks.remove(id);
    }

    void deleteSubtaskById(int id) {
        subtasks.remove(id);
    }

    void deleteEpicById(int id) {
        epics.remove(id);
    }
}
