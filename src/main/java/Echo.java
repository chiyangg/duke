import java.util.ArrayList;

public class Echo {
    private Ui ui;

    public Echo() {
        ui = new Ui();
    }
    public void run() {
        ui.showWelcome();

        String userInput;
        Storage storage = new Storage("./data/echo.txt");
        ArrayList<Task> tasks = storage.load();

        while (true) {
            userInput = ui.readCommand();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    ui.showBye();
                    break;
                }

                else if (userInput.equalsIgnoreCase("list")) {
                    horizontalLine();
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    horizontalLine();
                }

                // MARK / UNMARK
                else if (userInput.startsWith("mark ") || userInput.startsWith("unmark ")) {
                    String[] parts = userInput.split(" ");

                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new EchoException("Error: Task number is missing.");
                    }

                    try {
                        int index = Integer.parseInt(parts[1].trim()) - 1;
                        if (index < 0 || index >= tasks.size()) {
                            throw new EchoException("Error: Invalid task number.");
                        }

                        if (userInput.startsWith("mark ")) {
                            tasks.get(index).markAsDone();
                            storage.save(tasks);
                            horizontalLine();
                            System.out.println("Nice! I've marked this task as done:");
                        }
                        else {
                            tasks.get(index).markAsNotDone();
                            storage.save(tasks);
                            horizontalLine();
                            System.out.println("OK, I've marked this task as not done yet:");
                        }
                        System.out.println("  " + tasks.get(index));
                        horizontalLine();

                    } catch (NumberFormatException e) {
                        throw new EchoException("Error: Task number must be an integer.");
                    }
                }

                // TODOS
                else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5).trim();
                    if (description.isEmpty()) throw new EchoException("Error: Description is empty.");

                    Task t = new Todo(description);
                    tasks.add(t);
                    storage.save(tasks);
                    horizontalLine();
                    System.out.println(tasks.getLast().addedMessage(tasks.size()));
                    horizontalLine();
                }

                // DEADLINE
                else if (userInput.startsWith("deadline ")) {
                    String withoutCommand = userInput.substring(9);
                    String[] parts = withoutCommand.split("/by", 2);

                    if (parts.length < 2) {
                        throw new EchoException("Error: Missing /by time for deadline.");
                    }

                    String description = parts[0].trim();
                    String by = parts[1].trim();

                    if (description.isEmpty()) {
                        throw new EchoException("Error: Description is empty.");
                    }
                    if (by.isEmpty()) {
                        throw new EchoException("Error: /by time is empty.");
                    }

                    Task t = new Deadline(description, by);
                    tasks.add(t);
                    storage.save(tasks);
                    horizontalLine();
                    System.out.println(tasks.getLast().addedMessage(tasks.size()));
                    horizontalLine();
                }

                // EVENT
                else if (userInput.startsWith("event ")) {
                    String withoutCommand = userInput.substring(6);
                    String[] parts = withoutCommand.split("/from", 2);

                    if (parts.length < 2) {
                        throw new EchoException("Error: Missing /from time for event.");
                    }

                    String description = parts[0].trim();
                    if (description.isEmpty()) {
                        throw new EchoException("Error: Description is empty.");
                    }

                    String[] timeParts = parts[1].split("/to", 2);
                    if (timeParts.length < 2) {
                        throw new EchoException("Error: Missing /to time for event.");
                    }

                    String from = timeParts[0].trim();
                    String to = timeParts[1].trim();

                    if (from.isEmpty() || to.isEmpty()) {
                        throw new EchoException("Error: /from or /to time is empty.");
                    }

                    Task t = new Event(description, from, to);
                    tasks.add(t);
                    storage.save(tasks);
                    horizontalLine();
                    System.out.println(tasks.getLast().addedMessage(tasks.size()));
                    horizontalLine();
                }
                else if (userInput.startsWith("delete ")) {
                    String[] parts = userInput.split(" ");

                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new EchoException("Error: Task number is missing.");
                    }

                    if (parts[1].trim().equals("all")) {
                        tasks.clear();
                        storage.save(tasks);
                        horizontalLine();
                        System.out.println("All tasks have been removed.");
                        horizontalLine();
                    }

                    else {
                        try {
                            int index = Integer.parseInt(parts[1].trim()) - 1;
                            if (index < 0 || index >= tasks.size()) {
                                throw new EchoException("Error: Invalid task number.");
                            }

                            Task removedTask = tasks.remove(index);
                            storage.save(tasks);
                            horizontalLine();
                            System.out.println("Noted. I've removed this task:");
                            System.out.println("  " + removedTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            horizontalLine();

                        } catch (NumberFormatException e) {
                            throw new EchoException("Error: Task number must be an integer.");
                        }
                    }
                }

                // INVALID COMMAND
                else {
                    throw new EchoException("""
                            Error: Command not recognized. Available commands:
                            - todo <description>
                            - deadline <description> /by <time>
                            - event <description> /from <time> /to <time>
                            - list
                            - mark <index>
                            - unmark <index>
                            - delete <index>
                            - bye""");
                }

            } catch (EchoException e) {
                ui.showError(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        new Echo().run();

    }

    public static void horizontalLine() {
        System.out.println("___________________________________________");
    }
}
