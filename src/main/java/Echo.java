import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        System.out.println("___________________________________________");
        System.out.println("Hello! I'm Echo");
        System.out.println("What can I do for you?");
        System.out.println("___________________________________________");

        Scanner sc = new Scanner(System.in);
        String userInput;
        String[] tasks =  new String[100];
        int taskCounter = 0;

        while (true) {
            userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("___________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("___________________________________________");
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                System.out.println("___________________________________________");
                for (int i = 0; i < taskCounter; i++) {
                    System.out.println(i+1 + ". " + tasks[i]);
                }
                System.out.println("___________________________________________");
            }
            else {
                tasks[taskCounter] = userInput;
                taskCounter++;
                System.out.println("___________________________________________");
                System.out.println("added: " + userInput);
                System.out.println("___________________________________________");
            }
        }

        sc.close();


    }
}
