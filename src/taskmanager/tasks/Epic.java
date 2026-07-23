package taskmanager.tasks;

public class Epic extends Task {

    public Epic(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "taskmanager.tasks.Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                '}' + "\n";
    }
}