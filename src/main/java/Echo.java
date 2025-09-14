import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        horizontalLine();
        System.out.println("Hello! I'm Echo");
        System.out.println("What can I do for you?");
        horizontalLine();

        Scanner sc = new Scanner(System.in);
        String userInput;
        Task[] tasks =  new Task[100];
        int taskCounter = 0;

        while (true) {
            userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                horizontalLine();
                System.out.println("Bye. Hope to see you again soon!");
                horizontalLine();
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                horizontalLine();
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println(i+1 + ". " + tasks[i]);
                }
                horizontalLine();
            }

            else if (userInput.startsWith("mark ")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].markAsDone();
                horizontalLine();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[index]);
                horizontalLine();
            }

            else if (userInput.startsWith("unmark ")) {
                int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                tasks[index].markAsNotDone();
                horizontalLine();
                System.out.println("Nice! I've marked this task as not done yet:");
                System.out.println("  " + tasks[index]);
                horizontalLine();
            }

            else if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5).trim();
                tasks[taskCounter] = new Todo(description);
                taskCounter++;
                horizontalLine();
                System.out.println(tasks[taskCounter-1].addedMessage(taskCounter));
                horizontalLine();
            }

            else if (userInput.startsWith("deadline ")) {
                String withoutCommand = userInput.substring(9);
                String[] parts = withoutCommand.split("/by", 2);
                String description = parts[0].trim();
                String by = parts[1].trim();
                tasks[taskCounter] = new Deadline(description,by);
                taskCounter++;
                horizontalLine();
                System.out.println(tasks[taskCounter-1].addedMessage(taskCounter));
                horizontalLine();
            }
            else if (userInput.startsWith("event ")) {

                String withoutCommand = userInput.substring(6);
                String[] parts = withoutCommand.split("/from", 2);
                String description = parts[0].trim();
                String[] timeParts = parts[1].split("/to", 2);
                String from = timeParts[0].trim();
                String to = timeParts[1].trim();
                tasks[taskCounter] = new Event(description, from, to);
                taskCounter++;
                horizontalLine();
                System.out.println(tasks[taskCounter-1].addedMessage(taskCounter));
                horizontalLine();
            }

            else {
                tasks[taskCounter] = new Task(userInput);
                taskCounter++;
                horizontalLine();
                System.out.println("added: " + userInput);
                horizontalLine();
            }
        }

        sc.close();


    }

    public static void horizontalLine() {
        System.out.println("___________________________________________");
    }
}
