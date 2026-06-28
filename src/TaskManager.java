import java.util.List;

public interface TaskManager {

    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void createEpic(Epic epic);

    void getListAllTasks();

    Task getTaskById(int id);

    Subtask getSubtaskById(int id);

    Epic getEpicById(int id);

    void deleteAllTasks();

    void deleteTaskById(int id);

    void deleteSubtaskById(int id);

    void deleteEpicById(int id);

    List<Task> getHistory();

    void addHistory(Task task);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);
}