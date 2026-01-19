public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        // Создаём обычные задачи
        Task task1 = new Task("Магазин", "Купить хлеб", "NEW");
        Task task2 = new Task("Досуг", "Посмотреть новое кино", "NEW");

        manager.createTask(task1);
        manager.createTask(task2);

        // Создаём эпики
        Epic epic1 = new Epic("Ремонт", "Поклеить обои", "NEW");
        Epic epic2 = new Epic("Встреча с родственниками", "Организовать встречу", "NEW");

        manager.createEpic(epic1);
        manager.createEpic(epic2);

        // Создаём подзадачи для эпиков
        Subtask sub1 = new Subtask("Купить обои", "Выбрать и купить обои", "NEW", epic1.id);
        Subtask sub2 = new Subtask("Ресторан", "Выбрать и забронировать столик", "NEW", epic2.id);
        Subtask sub3 = new Subtask("Гости", "Позвонить Маме и Бабушке", "NEW", epic2.id);

        manager.createSubtask(sub1);
        manager.createSubtask(sub2);
        manager.createSubtask(sub3);

        // Выводим список всех задач
        manager.getListAllTasks();
        System.out.println();

        // Меняем статусы задачи и двух подзадач
        task1.status = "DONE";
        sub1.status = "DONE";
        sub3.status = "DONE";

        manager.updateTask(task1);
        manager.updateSubtask(sub1);
        manager.updateSubtask(sub3);

        // Список после изменения статусов
        manager.getListAllTasks();
        System.out.println();

        // Удаляем задачу и эпик с его подзадачами
        manager.deleteTaskById(task2.id);
        manager.deleteEpicById(epic1.id);

        // Список задач после удаления обычной задачи и эпика
        manager.getListAllTasks();
    }
}
