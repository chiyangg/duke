package echo;

public class Parser {
    public static Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new Todo(description);
            case "D" -> new Deadline(description, parts[3]);
            case "E" -> new Event(description, parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Unknown task type" + type);
        };

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
