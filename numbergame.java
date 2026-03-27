import java.util.Random;
import java.util.Scanner;

public class numbergame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int score = 0;
        int round = 1;
        String playAgain;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I will generate a number between 1 and 100.");
        System.out.println("You have limited attempts to guess it.");

        do {
            System.out.println("\n Round " + round);

            int number = random.nextInt(100) + 1; // Random number between 1 and 100
            int maxAttempts = 7;
            int attempts = 0;
            boolean guessedCorrectly = false;

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + "/" + maxAttempts + 
                                 " - Enter your guess: ");

                int guess;

                // Check if input is integer
                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt();
                } else {
                    System.out.println(" Please enter a valid number.");
                    scanner.next(); // Clear invalid input
                    continue;
                }

                attempts++;

                if (guess == number) {
                    System.out.println(" Correct! You guessed the number!");
                    score++;
                    guessedCorrectly = true;
                    break;
                } else if (guess < number) {
                    System.out.println(" Too low!");
                } else {
                    System.out.println(" Too high!");
                }
            }

            if (!guessedCorrectly) {
                System.out.println(" You've used all attempts!");
                System.out.println("The number was: " + number);
            }

            System.out.println(" Current Score: " + score);

            System.out.print("\nDo you want to play another round? (yes/no): ");
            playAgain = scanner.next();

            round++;

        } while (playAgain.equalsIgnoreCase("yes"));

        System.out.println("\n Game Over!");
        System.out.println("Final Score: " + score + " round(s) won.");

        scanner.close();
    }
}