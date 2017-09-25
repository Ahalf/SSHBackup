import java.util.Scanner;
import java.util.Random;

public class Controller
{
	protected static String choice;
	protected static String player;
	protected static char player_One = 'X';
	protected static char player_Two = 'O';
	protected static int movesMade = 0;

	Controller(String c, String p)
	{
		choice = c;
		player = p;
	}
	Controller()
	{
		choice = "0";
		player = "2";
		
	}
	Controller(String c)
	{
		choice = c;
		player = "0";
	}

    //returns the player token based on what round it is
	public static char GetPlayer(int itr)
	{
		if(itr % 2 == 0)
			return player_One;
		return player_Two;
	}
	
    //manages two human players, and returns the position they choose which will be used by the UpdateBoard function
	public int GetPosition(Board b, int turn)
	{

			if(turn % 2 == 0)
				System.out.print("Player 1 ");
			else
				System.out.print("Player 2 ");
			System.out.print("Enter Position: ");
			Scanner i = new Scanner(System.in);
			String input = i.nextLine();
		while (b.GetBoardState(Integer.valueOf(input)) != ' ')		//accounts for positons already taken
		{
			System.out.print("\nPosition taken. \n");
			System.out.print("Enter Position: ");
			i = new Scanner(System.in);
			input = i.nextLine();
			
		} 

		return Integer.valueOf(input);
	}
//handles winning moves. Row and Column wins done with for loop. Diagonals hard coded
public static boolean Win_or_lose(char[][]gameBoard, int rounds)
{
    int rowX = 0;
    int colX = 0;
    int rowO = 0;
    int colO = 0;
    
    // firstplayer diagonal wins
    boolean diagonalX = (gameBoard[0][0] == 'X' && gameBoard[1][1] == 'X' && gameBoard[2][2] == 'X') ||
    (gameBoard[0][2] == 'X' && gameBoard[1][1] == 'X' && gameBoard[2][0] == 'X');
    // second player diagonal wins
    boolean diagonalO = (gameBoard[0][0] == 'O' && gameBoard[1][1] == 'O' && gameBoard[2][2] == 'O') ||
    (gameBoard[0][2] == 'O' && gameBoard[1][1] == 'O' && gameBoard[2][0] == 'O');
    
    //***************************************
    // Finds column wins                    *
    //***************************************
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            if (gameBoard[j][i] == 'X')
                colX ++;
            else if (gameBoard[j][i] == 'O')
                colO ++;
        }
            
        // if 3 it means there are 3 of the same token in a row, signaling a win
        if (colX == 3)
        {
            System.out.println("Player 1 Wins!");
            System.out.println("Player 2 Loses!");
            return true;
        }
        else if (colO == 3)
        {
            System.out.println("Player 2 Wins!");
            System.out.println("Player 1 Loses!");
            return true;
        }
        //reset values for next column iteration
        colX = 0;
        colO = 0;
            
        
    }
    //***************************************
    // Finds row wins                       *
    //***************************************
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            if (gameBoard[i][j] == 'X')
                rowX ++;
            else if (gameBoard[i][j] == 'O')
                rowO ++;
        }
        if (rowX == 3)
        {
            System.out.println("Player 1 Wins!");
            System.out.println("Player 2 Loses!");
            return true;
        }
        else if (rowO == 3)
        {
            System.out.println("Player 2 Wins!");
            System.out.println("Player 1 Loses!");
            return true;
        }
            rowO = 0;
            rowX = 0;
    }

    //***************************************
    // Finds diagonal wins                  *
    //***************************************
    if (diagonalX)
    {
        System.out.println("Player 1 Wins!");
        System.out.println("Player 2 Loses!");
        return true;
    }
    if (diagonalO)
    {
        System.out.println("Player 2 Wins!");
        System.out.println("Player 1 Loses!");
        return true;
    }
    if (rounds == 8) //if tie
    {
        System.out.println("It's a tie!");
                
        return true;
    }
    return false;
    }
};



