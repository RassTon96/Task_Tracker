import java.util.List;

public interface TaskManager {

    void createTask(Task task);

    void createSubtask(Subtask subtask, int epicId);

    void createEpic(Epic epic);

    List<Task> getListAllTasks();

    List<Subtask> getListAllSubtasks();

    List<Epic> getListAllEpics();

    List<Subtask> getSubtasksOfEpic(int epicId);

    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpic();

    void deleteTaskById(int id);

    void deleteSubtaskById(int id);

    void deleteEpicById(int id);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);
}