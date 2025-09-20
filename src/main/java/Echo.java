import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        horizontalLine();
        System.out.println("Hello! I'm Echo");
        System.out.println("What can I do for you?");
        horizontalLine();

        Scanner sc = new Scanner(System.in);
        String userInput;
        Task[] tasks = new Task[100];
        int taskCounter = 0;

        while (true) {
            userInput = sc.nextLine();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    horizontalLine();
                    System.out.println("Bye. Hope to see you again soon!");
                    horizontalLine();
                    break;
                }

                else if (userInput.equalsIgnoreCase("list")) {
                    horizontalLine();
                    for (int i = 0; i < taskCounter; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
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
                        if (index < 0 || index >= taskCounter) {
                            throw new EchoException("Error: Invalid task number.");
                        }

                        if (userInput.startsWith("mark ")) {
                            tasks[index].markAsDone();
                            horizontalLine();
                            System.out.println("Nice! I've marked this task as done:");
                        }
                        else {
                            tasks[index].markAsNotDone();
                            horizontalLine();
                            System.out.println("OK, I've marked this task as not done yet:");
                        }
                        System.out.println("  " + tasks[index]);
                        horizontalLine();

                    } catch (NumberFormatException e) {
                        throw new EchoException("Error: Task number must be an integer.");
                    }
                }

                // TODOS
                else if (userInput.startsWith("todo ")) {
                    String description = userInput.substring(5).trim();
                    if (description.isEmpty()) throw new EchoException("Error: Description is empty.");

                    tasks[taskCounter] = new Todo(description);
                    taskCounter++;
                    horizontalLine();
                    System.out.println(tasks[taskCounter - 1].addedMessage(taskCounter));
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

                    tasks[taskCounter] = new Deadline(description, by);
                    taskCounter++;
                    horizontalLine();
                    System.out.println(tasks[taskCounter - 1].addedMessage(taskCounter));
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

                    tasks[taskCounter] = new Event(description, from, to);
                    taskCounter++;
                    horizontalLine();
                    System.out.println(tasks[taskCounter - 1].addedMessage(taskCounter));
                    horizontalLine();
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
                            - bye""");
                }

            } catch (EchoException e) {
                horizontalLine();
                System.out.println(e.getMessage());
                horizontalLine();
            }
        }

        sc.close();
    }

    public static void horizontalLine() {
        System.out.println("___________________________________________");
    }
}
