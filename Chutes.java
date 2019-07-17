import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Chutes {

	/**
	 * Reads all chutes and ladders values from file into two arrays.
	 * Prompts user for number of players and names.  Allows repeat games.
	 *
	 * @param args A string array containing the command line arguments.
	 */
	public static void main(String[] args) throws IOException {

		// constants of the game and the board
		final int NUM_SQUARES = 100;
		final int MIN_PLAYERS = 2;
		final int MAX_PLAYERS = 6;
		// data file containing locations of chutes and ladders.
		final String dataFileName = "p3input.txt";

		// variable declaration
		Random rand = new Random();
		Scanner keyboard = new Scanner(System.in);
		String dividingLine = "\n------------------------\n";
		String playerName;

		int numPlayers;  // number of players, user-provided
		int pNum;   // player number (the turn-based counter, and array index)
		int spinner;     // integer, from 1 to 6
		int itemNum;  // number of the "item" (the chute or ladder)

		String responseToRepeat;
		char repeat;


		// Chutes and Ladders appear on the board. That data is stored in a file.
		// get length of data file. # of rows is total chutes and ladders in game.
		File myFile = new File(dataFileName);
		Scanner scanIn = new Scanner(myFile);
		int fileLength = getFileLength(scanIn);

		// populate data arrays:
		// column 1 is start position of a chute or ladder (a.k.a. "item").
		int[] itemStarts = new int[fileLength];

		// column 2 is offset -- bonus or penalty of "item" (ladder/chute).
      int[] itemOffset = new int[fileLength];

		scanIn = new Scanner(myFile);  // reinstantiate object for re-use.
		populateChutesLaddersData(itemStarts, itemOffset, scanIn);


	   // Welcome
		System.out.printf("Welcome to Chutes & Ladders! You must land on %d " +
		"(without going past) \nto win! You will play against the computer.",
		NUM_SQUARES);


		// game loop

		do {
			// Get number of players.
			// The valid limits are set by MIN_PLAYERS and MAX_PLAYERS.
			System.out.printf("\n%s\nHow many players will play " +
						"(between %d-%d)? ", dividingLine, MIN_PLAYERS, MAX_PLAYERS);
			numPlayers = getInt(keyboard);
			while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
				System.out.println("Sorry. Invalid number entered.");
				System.out.printf("How many players will play (between %d-%d)? ",
										MIN_PLAYERS, MAX_PLAYERS);
				numPlayers = getInt(keyboard);
			}


			// numPlayers is determined by user per game iteration.
			// Now array can be constructed.
			String[] playerNames = new String[numPlayers];  // names of players


			// Requests a name for each player.
			for (pNum = 0; pNum < numPlayers; pNum++) {
				playerName = getString(keyboard, "Enter player %d's name: ", pNum + 1);
				playerNames[pNum] = playerName;
			}
			System.out.println();

			// players have counters which start on the theoretical square 0.
			int[] playerLoc = new int[numPlayers];	 // positions of players

			// initialize inner-loop variables.
			pNum = 0;  // this is the turn-counter.  0 indicates player 1.


			// As long as the player hasn't won yet, the game goes on.
			while (playerLoc[pNum] != 100) {
	         System.out.printf("%s, it's your turn. You are currently at " +
				"space %d.\n", playerNames[pNum], playerLoc[pNum]);

				// spins the randomly-generated number spinner
	         spinner = rand.nextInt(6) + 1;
	         System.out.printf("The spin was: %d\n", spinner);
				if (playerLoc[pNum] + spinner > 100) {
					// A player has to reach 100 exactly; no more, no less.
				   System.out.printf("Sorry, no player can go over 100.\n\n");

				} else {
					// player advances movement.
					playerLoc[pNum] += spinner;


					// Chute or ladder checking and offsetting.
					itemNum = linearSearch(itemStarts, playerLoc[pNum]);

					// if linearSearch returns any non-negative value,
					// then the player has landed on a chute or ladder.
					if (itemNum != -1) {

						// thus they are moved ahead or behind (the offset).
						playerLoc[pNum] += itemOffset[itemNum];

						if (itemOffset[itemNum] < 0) {
							// It's a chute! :(
							System.out.printf("Sorry, that is a chute! " +
							"You are sent back %d spaces.\n", itemOffset[itemNum]);
						} else {

							// It's a ladder! :(
							System.out.printf("Congrats, that is a ladder! " +
							"You climb ahead %d spaces.\n", itemOffset[itemNum]);
						}
					}
					System.out.printf("You are now at space %d.\n\n",
											playerLoc[pNum]);
				}
				// if the player has not won yet,
				// then the game moves on to the next player.
				if (playerLoc[pNum] != 100) {
					pNum++;

					// if the last player has played,
					// then the game goes back to the first player.
					if (pNum == numPlayers) {
						pNum = 0;
					}
				}

			}

				// Congratulate winner for the victory!
				System.out.printf("%s, you have won the game!\n\n",
										playerNames[pNum]);

				// prompt user to repeat.
				System.out.print("Do you want to play again (y/n)? ");
				responseToRepeat = keyboard.nextLine();
				repeat = responseToRepeat.charAt(0);

		} while (repeat == 'y' || repeat == 'Y');
		// closes game loop

		// exit game
		System.out.printf("%s%s", dividingLine, dividingLine);
		System.out.println("\nWe hope you enjoyed Chutes and Ladders!\n" +
			"Have a great day!\n");


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
	 * method: getFileLength
	 * Reads from file and returns fileLength.
	 *
	 * @param scanIn  Scanner object.
	 * @return  number of rows of file.
	 */
	public static int getFileLength(Scanner scanIn) throws IOException {
	   int fileLength = 0;

	   // Loop through data file rows to get file size.
	   while (scanIn.hasNext()) {
	      fileLength++;
	      scanIn.nextLine();
	   }

	   scanIn.close();

		return fileLength;
	}


	/**
	 * method: populateChutesLaddersData
	 * Reads from file, returns starting points of chutes and ladders (column 1).
	 * Player moves through chute/ladder when their position equals this value.
	 * Also returns offset in column 2 (bonus of ladder, or penalty of chute).
	 *
	 * @param arr1  Array of integers, ranging from 0 to NUM_SQUARES.
	 * @param arr2  Array of integers, negative or positive (not zero)
	 * @param scanIn  Scanner object.
	 */
	public static void populateChutesLaddersData(int[] arr1, int[] arr2,
													 Scanner scanIn) throws IOException {
		int numRow = 0;

	   // Loop through data file rows.
	   while (scanIn.hasNext()) {
			// column 1: start position of chute or ladder.
			arr1[numRow] = scanIn.nextInt();
			// column 2: offset of chute or ladder (in column 1).
			arr2[numRow] = scanIn.nextInt();

			numRow++;
	   }

	   scanIn.close();
	}


	/**
	 *	Performs linear search on input array and returns matching index location.
	 *
	 * @param arr Integer array.
	 * @param key Integer of search target value.
	 * @return    If key is found, non-negative integer index; -1 if not found.
	 */
	public static int linearSearch(int[] arr, int key) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == key)
				return i;
		}
		return -1;
	}

}
