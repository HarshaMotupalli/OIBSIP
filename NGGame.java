import java.util.Random;
import java.util.Scanner;

public class NGGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        int maxAttempts = 5;

        while (true) {
            System.out.println("\n**************************************");
            System.out.println("    Welcome to the Number Guessing Game");
            System.out.println("****************************************");
            System.out.println("1. Play the Game");
            System.out.println("2. View Scoreboard");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    int newScore = playGame(scanner, random, maxAttempts);
                    if (newScore > score) {
                        score = newScore;
                    }
                    break;
                case 2:
                    System.out.println("Your Score: " + score);
                    break;
                case 3:
                    System.out.println("Exiting the game. Thank you for playing!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static int playGame(Scanner scanner, Random random, int maxAttempts) {
        System.out.print("Enter the number range (e.g., 100): ");
        int range = scanner.nextInt();

        int randomNumber = random.nextInt(range) + 1;
        int attempts = maxAttempts;
        int score = 0;

        while (attempts > 0) {
            System.out.printf("Guess a number between 1 and %d: ", range);
            int userGuess = scanner.nextInt();

            if (userGuess == randomNumber) {
                System.out.println("Congratulations! You've guessed the number correctly.");
                score++;
                break;
            } else if (userGuess < randomNumber) {
                System.out.println("Your guess is too low. Try again.");
            } else {
                System.out.println("Your guess is too high. Try again.");
            }

            attempts--;
            if (attempts > 0) {
                System.out.printf("You have %d attempts left.\n", attempts);
            }
        }

        if (attempts == 0) {
            System.out.printf("Sorry, you've run out of attempts. The number was %d.\n", randomNumber);
        }

        return score;
    }
}