package taskmanager;

import taskmanager.history.HistoryManager;
import taskmanager.manager.InMemoryTaskManager;
import taskmanager.manager.TaskManager;
import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;
import taskmanager.util.Managers;

public class Main {
    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = new InMemoryTaskManager(historyManager);
        // Создание обычных задач
        Task task1 = new Task("Магазин", "Купить хлеб");
        Task task2 = new Task("Досуг", "Посмотреть новое кино");
        Task task3 = new Task("Занятия", "Посмотреть запись лекции");
        Task task4 = new Task("Спорт", "Пробежать 5 километров");

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.createTask(task4);

        // Создание эпиков
        Epic epic1 = new Epic("Ремонт", "Поклеить обои");
        Epic epic2 = new Epic("Встреча с родственниками", "Организовать встречу");
        Epic epic3 = new Epic("Поход", "Организовать поход");

        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createEpic(epic3);

        // Создание подзадач
        Subtask sub1 = new Subtask("Купить обои", "Выбрать и купить обои");
        Subtask sub2 = new Subtask("Ресторан", "Выбрать и забронировать столик");
        Subtask sub3 = new Subtask("Гости", "Позвонить Маме и Бабушке");
        Subtask sub4 = new Subtask("Маршрут", "Проложить маршрут");
        Subtask sub5 = new Subtask("Закуп", "Закупить продукты");
        Subtask sub6 = new Subtask("Расходы", "Посчитать расходы на поход");

        manager.createSubtask(sub1, epic1.getId());
        manager.createSubtask(sub2, epic2.getId());
        manager.createSubtask(sub3, epic2.getId());
        manager.createSubtask(sub4, epic3.getId());
        manager.createSubtask(sub5, epic3.getId());
        manager.createSubtask(sub6, epic3.getId());

        System.out.println("Вывод списка всех обычных задач:" +
                "\n" + manager.getListAllTasks() + "\n");

        System.out.println("Вывод списка всех подзадач:" +
                "\n" + manager.getListAllSubtasks() + "\n");

        System.out.println("Вывод списка всех эпиков:" +
                "\n" + manager.getListAllEpics() + "\n");

        System.out.println("Изменение статусов обычной задачи и двух подзадач:");
        Task taskToUpdate = new Task("Магазин", "Купить хлеб");
        taskToUpdate.setStatus(Task.Status.DONE);
        taskToUpdate.setId(task1.getId());
        manager.updateTask(taskToUpdate);

        Subtask subtaskToUpdate = new Subtask("Купить обои", "Выбрать и купить обои");
        subtaskToUpdate.setStatus(Task.Status.DONE);
        subtaskToUpdate.setId(sub1.getId());
        subtaskToUpdate.setEpicId(sub1.getEpicId());
        manager.updateSubtask(subtaskToUpdate);

        subtaskToUpdate = new Subtask("Гости", "Позвонить Маме и Бабушке");
        subtaskToUpdate.setStatus(Task.Status.DONE);
        subtaskToUpdate.setId(sub3.getId());
        subtaskToUpdate.setEpicId(sub3.getEpicId());
        manager.updateSubtask(subtaskToUpdate);
        System.out.println();

        System.out.println("Вывод списка всех обычных задач после изменения статусов:" +
                "\n" + manager.getListAllTasks() + "\n");

        System.out.println("Вывод списка всех подзадач после изменения статусов:" +
                "\n" + manager.getListAllSubtasks() + "\n");

        System.out.println("Вывод списка всех эпиков после изменения статусов:" +
                "\n" + manager.getListAllEpics() + "\n");

        System.out.println("Удаление обычной задачи и эпика с его подзадачами");
        manager.deleteTaskById(task2.getId());
        manager.deleteEpicById(epic1.getId());

        System.out.println("Вывод списка всех обычных задач после удаления обычной задачи:" +
                "\n" + manager.getListAllTasks() + "\n");

        System.out.println("Вывод списка всех подзадач после удаления эпика с подзадачами:" +
                "\n" + manager.getListAllSubtasks() + "\n");

        System.out.println("Вывод списка всех эпиков после удаления эпика:" +
                "\n" + manager.getListAllEpics() + "\n");

        System.out.println("Вызов обычных задач для проверки метода истории:" +
                "\n" + manager.getTaskById(0)
                + manager.getTaskById(2) + manager.getTaskById(3));

        System.out.println("Вызов подзадач для проверки метода истории:" +
                "\n" + manager.getSubtaskById(8) + manager.getSubtaskById(9) + manager.getSubtaskById(10) +
                manager.getSubtaskById(11) + manager.getSubtaskById(12));

        System.out.println("Вызов эпиков для проверки метода истории:" +
                "\n" + manager.getEpicById(5) + manager.getEpicById(6));

        System.out.println("Вызов метода истории просмотра:"
                + "\n" + historyManager.getHistory());
    }
}