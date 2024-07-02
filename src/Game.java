import java.util.Random;
import java.util.Scanner;


public class Game {

	public final int MAX_NUMBER = 100;
	private int randomNumber;
	private int previousNumber; // probably could be worked around
	private int guessCounter;
	private int rightGuessesCounter;
	private boolean nextRound; // probably could be worked around

	public Game() {
		this.guessCounter = 0;
		this.rightGuessesCounter = 0;
		this.nextRound = true;
	}

	public void startGame() {
		generateNumber();
		while (this.nextRound) {
			nextRound();
		}
	}

	//	generate randomNumber 0-MAX;
	public void generateNumber() {
		Random random = new Random();

		if (this.randomNumber != 0) {
			this.previousNumber = this.randomNumber;
		}

		do {
			this.randomNumber = random.nextInt(MAX_NUMBER) + 1;
		} while (this.randomNumber == this.previousNumber);

		System.out.println("The number is: " + this.randomNumber);
	}

	//	show randomNumber & ask user for a guess (HIGHER/LOWER);
	public Guess takeGuess() {
		Scanner scanner = new Scanner(System.in);
		Guess userGuess = null;

		char input;
		do {
			System.out.println("Guess if the next number will be HIGHER or LOWER? (h/l)");
			input = scanner.next().charAt(0);
		} while (input != 'h' && input != 'l');

		switch (input) {
			case 'h':
				userGuess = Guess.HIGHER;
				break;
			case 'l':
				userGuess = Guess.LOWER;
				break;
		}
		this.guessCounter++;
		return userGuess;
	}

	public void nextRound() {
		Guess userGuess = takeGuess();
		generateNumber();
		boolean correctGuess = checkGuess(userGuess);
		roundResults(correctGuess);

		Scanner scanner = new Scanner(System.in);
		char input;

		do {
			System.out.println("Want to guess again? (y/n)");
			input = scanner.next().charAt(0);
		} while (input != 'y' && input != 'n');

		if (input == 'n') {
			this.nextRound = false;
		}
		gameEnded();
	}

	// compare
	public boolean isHigher() {
		return this.randomNumber > this.previousNumber;
	}

	// check if guess matches result;
	public boolean checkGuess(Guess userGuess) {
		if (isHigher() && (userGuess == Guess.HIGHER) ||
			!isHigher() && (userGuess == Guess.LOWER)) {
			this.rightGuessesCounter++;
			return true;
		} else {
			return false;
		}
	}

	public void roundResults(boolean correctGuess) {
		if (correctGuess) {System.out.println("Correct guess!");}
		else {System.out.println("Incorrect guess!");}
	}

	public void gameEnded() {
		if (!this.nextRound) {
			System.out.println("Thanks for playing, your results are:");
			System.out.println("You've played " + this.guessCounter + " rounds.");
			System.out.println("You were right " + this.rightGuessesCounter + " times.");
			float winrate = (float) this.rightGuessesCounter / (float) this.guessCounter * 100;
			System.out.printf("That is %.2f%% winrate!", winrate);
		}
	}
}

