public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        // Создаём обычные задачи
        Task task1 = new Task("Магазин", "Купить хлеб", "NEW");
        Task task2 = new Task("Досуг", "Посмотреть новое кино", "NEW");
        Task task3 = new Task("Занятия", "Посмотреть запись лекции", "NEW");
        Task task4 = new Task("Спорт", "Пробежать 5 километров", "NEW");

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);
        manager.createTask(task4);

        // Создаём эпики
        Epic epic1 = new Epic("Ремонт", "Поклеить обои", "NEW");
        Epic epic2 = new Epic("Встреча с родственниками", "Организовать встречу", "NEW");
        Epic epic3 = new Epic("Поход", "Организовать поход", "NEW");

        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createEpic(epic3);

        // Создаём подзадачи для эпиков
        Subtask sub1 = new Subtask("Купить обои", "Выбрать и купить обои", "NEW", epic1.id);
        Subtask sub2 = new Subtask("Ресторан", "Выбрать и забронировать столик", "NEW", epic2.id);
        Subtask sub3 = new Subtask("Гости", "Позвонить Маме и Бабушке", "NEW", epic2.id);
        Subtask sub4 = new Subtask("Маршрут", "Проложить маршрут", "NEW", epic3.id);
        Subtask sub5 = new Subtask("Закуп", "Закупить продукты", "NEW", epic3.id);
        Subtask sub6 = new Subtask("Расходы", "Посчитать расходы на поход", "NEW", epic3.id);

        manager.createSubtask(sub1);
        manager.createSubtask(sub2);
        manager.createSubtask(sub3);
        manager.createSubtask(sub4);
        manager.createSubtask(sub5);
        manager.createSubtask(sub6);

        // Выводим список всех задач
        //manager.getListAllTasks();

        // Меняем статусы задачи и двух подзадач
        task1.status = "DONE";
        sub1.status = "DONE";
        sub3.status = "DONE";

        manager.updateTask(task1);
        manager.updateSubtask(sub1);
        manager.updateSubtask(sub3);

        // Список после изменения статусов
        //manager.getListAllTasks();

        // Удаляем задачу и эпик с его подзадачами
//        manager.deleteTaskById(task2.id);
//        manager.deleteEpicById(epic1.id);

        // Список задач после удаления обычной задачи и эпика
        manager.getListAllTasks();

        manager.getTaskById(0);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);

        manager.getSubtaskById(7);
        manager.getSubtaskById(8);
        manager.getSubtaskById(9);
        manager.getSubtaskById(10);
        manager.getSubtaskById(11);
        manager.getSubtaskById(12);

        manager.getEpicById(4);
        manager.getEpicById(5);
        manager.getEpicById(6);

        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}