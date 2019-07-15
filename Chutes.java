public class Chutes {

	// constants of the game's board
	public final int NUM_SQUARES = 100;

	public static void main(String[] args) {
		// variable declaration

		// String variables
		String welcome = "Welcome to Chutes & Ladders!  ...  ";

		int numPlayers = 2;  // (btw final code is done)

		// With numPlayers assigned by user, array lengths can be fixed.
		String[] playerNames = {"Abe", "Bob"};  // names of players

		int[] playerPositions = new int[numPlayers];	// positions of players

		// declarations of inner-loop variables
		int playerNum;
		int spinner;
		// prototype before a loop
		playerNum = 0;
		System.out.printf("%s, it's your turn. You are currently at space %d.\n", playerNames[playerNum], playerPositions[playerNum]);
		spinner = 50;
		System.out.printf("The spin was: %d\n", spinner);
		playerPositions[playerNum] += spinner;
		System.out.printf("You are now at space %d.\n\n", playerPositions[playerNum]);

		// next player
		playerNum++;
		System.out.printf("%s, it's your turn. You are currently at space %d.\n", playerNames[playerNum], playerPositions[playerNum]);
		spinner = 50;
		System.out.printf("The spin was: %d\n", spinner);
		playerPositions[playerNum] += spinner;
		System.out.printf("You are now at space %d.\n\n", playerPositions[playerNum]);

		// back to player 1
		playerNum--;
		System.out.printf("%s, it's your turn. You are currently at space %d.\n", playerNames[playerNum], playerPositions[playerNum]);
		spinner = 50;
		System.out.printf("The spin was: %d\n", spinner);
		playerPositions[playerNum] += spinner;
		System.out.printf("You are now at space %d.\n\n", playerPositions[playerNum]);

		System.out.printf("%s, you have won the game!\n\n", playerNames[playerNum]);

	}

}
