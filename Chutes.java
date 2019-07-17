import java.util.Random;

public class Chutes {

	// constants of the game's board
	public final int NUM_SQUARES = 100;



	public static void main(String[] args) {
		// variable declaration
		Random rand = new Random();

		// String variables
		String welcome = "Welcome to Chutes & Ladders!  ...  ";

		int numPlayers = 2;  // (btw final code is done)

		// With numPlayers assigned by user, array lengths can be fixed.
		String[] playerNames = {"Abe", "Bob"};  // names of players

		int[] playerPositions = {95, 95};	// positions of players

		// declarations of inner-loop variables
		int playerNum = 0;
		int spinner;

		while (playerPositions[playerNum] != 100)
		{
         System.out.printf("%s, it's your turn. You are currently at space %d.\n", playerNames[playerNum], playerPositions[playerNum]);

         spinner = rand.nextInt(6) + 1;
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


	}

}
