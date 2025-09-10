import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        System.out.println("___________________________________________");
        System.out.println("Hello! I'm Echo");
        System.out.println("What can I do for you?");
        System.out.println("___________________________________________");

        Scanner sc = new Scanner(System.in);
        String userInput;

        while (true) {
            userInput = sc.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println("___________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("___________________________________________");
                break;
            }
            else {
                System.out.println("___________________________________________");
                System.out.println(userInput);
                System.out.println("___________________________________________");
            }
        }


    }
}
