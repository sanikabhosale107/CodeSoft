package multithreading;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame() {
        // Step 1: Generate a random number within the specified range
        int lowerLimit = 1;
        int upperLimit = 100;
        int targetNumber = new Random().nextInt(upperLimit - lowerLimit + 1) + lowerLimit;

        // Additional details
        int maxAttempts = 10;
        int roundsPlayed = 0;
        int totalAttempts = 0;
        int roundsWon = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nRound: " + (roundsPlayed + 1));
            System.out.println("Score: " + roundsWon + " rounds won with " + totalAttempts + " total attempts");

            // Step 2: Prompt the user to enter their guess
            System.out.print("Guess the number between " + lowerLimit + " and " + upperLimit + ": ");
            int userGuess = scanner.nextInt();

            // Step 3: Compare the user's guess and provide feedback
            if (userGuess == targetNumber) {
                System.out.println("Congratulations! You guessed the correct number.");
                roundsWon++;
                break;
            } else if (userGuess < targetNumber) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }

            // Update attempts and check if the maximum attempts are reached
            totalAttempts++;
            if (totalAttempts == maxAttempts) {
                System.out.println("Sorry, you've reached the maximum number of attempts. The correct number was " + targetNumber + ".");
                break;
            }
        }

        // Step 6: Ask if the user wants to play again
        System.out.print("Do you want to play again? (yes/no): ");
        String playAgain = scanner.next();
        if (playAgain.equalsIgnoreCase("yes")) {
            playGame();
        } else {
            System.out.println("Thanks for playing! Goodbye.");
        }

        scanner.close();
    }
}
