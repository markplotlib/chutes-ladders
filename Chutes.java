public class Dev {

	// constants of the game's board
	public final int NUM_SQUARES = 100;



	public static void main(String[] args) {
		// variable declaration
		String responseToRepeat;
		char repeat;

		do {
			int numPlayers = 2;  // (btw final code is done)

			// With numPlayers assigned by user, array lengths can be fixed.
			String[] playerNames = {"Abe", "Bob"};  // names of players

			int[] playerPositions = new int[numPlayers];	// positions of players

			// declarations of inner-loop variables
			int playerNum = 0;
			int spinner;

			while (playerPositions[playerNum] != 100)
			{
	         System.out.printf("%s, it's your turn. You are currently at space %d.\n", playerNames[playerNum], playerPositions[playerNum]);

	         spinner = 50;
	         System.out.printf("The spin was: %d\n", spinner);
				if (playerPositions[playerNum] + spinner > 100) {
				   System.out.printf("Sorry, no player can go over 100.\n\n");
				} else {
				   playerPositions[playerNum] += spinner;
					System.out.printf("You are now at space %d.\n\n", playerPositions[playerNum]);
				}
				if (playerPositions[playerNum] != 100) {
					playerNum++;
					if (playerNum == numPlayers) {
						playerNum = 0;
					}
				}
			}

			System.out.printf("%s, you have won the game!\n\n", playerNames[playerNum]);

			System.out.print("Do you want to play again (y/n)? ");
			responseToRepeat = keyboard.nextLine();
			repeat = responseToRepeat.charAt(0);

		} while (repeat == 'y' || repeat == 'Y');

	}

}
