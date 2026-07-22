public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создаём обычные задачи
        Task task1 = new Task("Магазин", "Купить хлеб");
        Task task2 = new Task("Досуг", "Посмотреть новое кино", Task.Status.NEW);
        Task task3 = new Task("Занятия", "Посмотреть запись лекции", Task.Status.NEW);
        Task task4 = new Task("Спорт", "Пробежать 5 километров", Task.Status.NEW);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.createTask(task4);

        // Создаём эпики
        Epic epic1 = new Epic("Ремонт", "Поклеить обои", Task.Status.NEW);
        Epic epic2 = new Epic("Встреча с родственниками", "Организовать встречу", Task.Status.NEW);
        Epic epic3 = new Epic("Поход", "Организовать поход", Task.Status.NEW);

        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createEpic(epic3);

        // Создаём подзадачи для эпиков
        Subtask sub1 = new Subtask("Купить обои", "Выбрать и купить обои", Task.Status.NEW, epic1.id);
        Subtask sub2 = new Subtask("Ресторан", "Выбрать и забронировать столик", Task.Status.NEW, epic2.id);
        Subtask sub3 = new Subtask("Гости", "Позвонить Маме и Бабушке", Task.Status.NEW, epic2.id);
        Subtask sub4 = new Subtask("Маршрут", "Проложить маршрут", Task.Status.NEW, epic3.id);
        Subtask sub5 = new Subtask("Закуп", "Закупить продукты", Task.Status.NEW, epic3.id);
        Subtask sub6 = new Subtask("Расходы", "Посчитать расходы на поход", Task.Status.NEW, epic3.id);

        manager.createSubtask(sub1);
        manager.createSubtask(sub2);
        manager.createSubtask(sub3);
        manager.createSubtask(sub4);
        manager.createSubtask(sub5);
        manager.createSubtask(sub6);

        // Выводим список всех задач
        manager.getListAllTasks();

        // Меняем статусы задачи и двух подзадач
        Task taskToUpdate = new Task("Магазин", "Купить хлеб", Task.Status.DONE);
        taskToUpdate.id = task1.id;
        manager.updateTask(taskToUpdate);

        Subtask subtaskToUpdate = new Subtask("Купить обои", "Выбрать и купить обои", Task.Status.DONE, epic1.id);
        subtaskToUpdate.id = sub1.id;
        manager.updateSubtask(subtaskToUpdate);

        subtaskToUpdate = new Subtask("Гости", "Позвонить Маме и Бабушке", Task.Status.DONE, epic2.id);
        subtaskToUpdate.id = sub3.id;
        manager.updateSubtask(subtaskToUpdate);

        // Список после изменения статусов
        manager.getListAllTasks();

        // Удаляем задачу и эпик с его подзадачами
        manager.deleteTaskById(task2.id);
        manager.deleteEpicById(epic1.id);

        // Список задач после удаления обычной задачи и эпика
        manager.getListAllTasks();

        // Вызов задач для проверки метода истории
        manager.getTaskById(0);
        manager.getTaskById(2);
        manager.getTaskById(3);

        manager.getSubtaskById(7);
        manager.getSubtaskById(8);
        manager.getSubtaskById(9);
        manager.getSubtaskById(10);
        manager.getSubtaskById(11);
        manager.getSubtaskById(12);

        manager.getEpicById(5);
        manager.getEpicById(6);

        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}