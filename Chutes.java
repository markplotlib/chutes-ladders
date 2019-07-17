import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Chutes {



	public static void main(String[] args) throws IOException {
		// constants of the game and the board
		final int NUM_SQUARES = 100;
		final int MIN_PLAYERS = 2;
		final int MAX_PLAYERS = 6;
		// data file containing locations of chutes and ladders.
		final String dataFileName = "chutes-90s.txt";

		// variable declaration
		Random rand = new Random();
		Scanner keyboard = new Scanner(System.in);
		String responseToRepeat;
		char repeat;

		// double-array: chutesLaddersData
		int[][] chutesLaddersData;
		chutesLaddersData = getChutesLadders(dataFileName);


		do {
			String name;
			int numPlayers;

			// Get number of players: limits set by MIN_PLAYERS and MAX_PLAYERS.
			System.out.printf("How many players will play (between %d-%d)? ",
									MIN_PLAYERS, MAX_PLAYERS);
			numPlayers = getInt(keyboard);
			while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
				System.out.println("Sorry. Invalid number entered.");
				System.out.printf("How many players will play (between %d-%d)? ",
										MIN_PLAYERS, MAX_PLAYERS);
				numPlayers = getInt(keyboard);
			}
			// keyboard.nextLine();

			// With numPlayers assigned by user, array lengths can be fixed.
			String[] playerNames = new String[numPlayers];  // names of players

			// Requests a name for each player.
			for (int playerNum = 0; playerNum < numPlayers; playerNum++) {
				name = getString(keyboard, "Enter player %d's name: ", playerNum + 1);
				playerNames[playerNum] = name;
			}
			System.out.println();
			// players have counters which start on the theoretical square 0.
			int[] playerPositions = new int[numPlayers];	 // positions of players

			// declarations of inner-loop variables
			int playerNum = 0;
			int spinner;

			while (playerPositions[playerNum] != 100) {
	         System.out.printf("%s, it's your turn. You are currently at " +
				"space %d.\n", playerNames[playerNum], playerPositions[playerNum]);

	         spinner = rand.nextInt(6) + 1;
	         System.out.printf("The spin was: %d\n", spinner);
				if (playerPositions[playerNum] + spinner > 100) {
				   System.out.printf("Sorry, no player can go over 100.\n\n");
				} else {
				   playerPositions[playerNum] += spinner;
					System.out.printf("You are now at space %d.\n\n",
											playerPositions[playerNum]);
				}
				if (playerPositions[playerNum] != 100) {
					playerNum++;
					if (playerNum == numPlayers) {
						playerNum = 0;
					}
				}

			}

				System.out.printf("%s, you have won the game!\n\n",
										playerNames[playerNum]);

				System.out.print("Do you want to play again (y/n)? ");
				responseToRepeat = keyboard.nextLine();
				repeat = responseToRepeat.charAt(0);

		} while (repeat == 'y' || repeat == 'Y');

	keyboard.close();
	}


	/**
    * method: getString
    * Prompts user for text input through console.
    *
    * @param keyboard  Scanner object.
	 * @param prompt    Question or statement displayed to ask user for specific input.
	 * @param counter   Number to appear in prompt.
    * @return          User-provided text input.
    */
   public static String getString(Scanner keyboard, String prompt, int counter) {
      String text;

      System.out.printf(prompt, counter);
      text = keyboard.nextLine();

      return text;
   }


   /**
    * method: getInt
    * Gets number (of type integer) through console.
    *
    * @param keyboard  Scanner object.
    * @return          User-provided number input (type integer).
    */
   public static int getInt(Scanner keyboard) {
      int num = keyboard.nextInt();
		keyboard.nextLine();

      return num;
   }


	/**
	 * method: getChutesLadders
	 * Reads from file and returns 2D array of data.
	 * column 1 is start position of a chute or ladder.
	 * column 2 is offset (bonus of ladder, or penalty of chute) for column 1.
	 * number of rows of double-array equal to number of rows of input data file.
	 *
	 * @param filePath  Data filename.
	 * @return  double-array (2D array).
	 */
	public static int[][] getChutesLadders(String filePath) throws IOException {
	   File myFile = new File(filePath);
	   Scanner scanIn = new Scanner(myFile);

	   int fileLength = 0, rowNum = 0;  // each row points to 1 chute or ladder.

	   // Loop through data file rows to get file size.
	   while (scanIn.hasNext()) {
	      fileLength++;
	      scanIn.nextInt();
	   }

	   scanIn.close();

		scanIn = new Scanner(myFile);
		int[][] data = new int[fileLength][2];

		// Loop through data file rows to get file size.
		while (scanIn.hasNext()) {
			// column 1: start position of chute or ladder.
			data[rowNum][0] = scanIn.nextInt();
			// column 2: offset of chute or ladder (in column 1).
			data[rowNum][1] = scanIn.nextInt();
			rowNum++;
		}

	   return data;
	}

}
