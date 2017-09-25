 

class Board
{
	private static char [][] board = new char[3][3];
	private static char[][] positionBoard = {{'1','2','3'},{'4','5','6'},{'7','8','9'}};

	//initializes board to all spaces
	Board()
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				board[i][j] = ' ';
			}
		}
	}
	//prints the actual playing board
	public static void PrintSingleBoard(char [][] board, int i)
	{
		for(int j = 0; j < 3; j++)
			{
				System.out.print(board[i][j]);
				if(j != 2)
					System.out.print(" | ");

			}
	}

	//prints the reference board with position numbers
	public static void PrintBoard()
	{
		String bar = "                                  ";
		System.out.printf("\nGame Board:                                Positions:\n");
		
		for (int i = 0; i < 3; i++)
		{
			PrintSingleBoard(board, i);
			System.out.print(bar);
			PrintSingleBoard(positionBoard, i);
			System.out.println();

			if(i != 2)
				System.out.print("---------" + bar + "---------\n");
		}
	}

	//adds a player token to a position on the board
	public static void UpdateBoard(int index, char player)
	{
		
		switch(index)
		{
			case 1:
			case 2:
			case 3:
				board[0][index - 1] = player;
				break;
			case 4:
			case 5:
			case 6:
				board[1][index - 4] = player;
				break;
			case 7:
			case 8:
			case 9:
				board[2][index - 7] = player;
				break;
			default:
				break;
		}
	}
	//returns the character located at a specific position in the board
	public static char GetBoardState(int index)
	{
		switch(index)
		{
			case 1:
			case 2:
			case 3:
				return board[0][index - 1];
			case 4:
			case 5:
			case 6:
				return board[1][index - 4];
			case 7:
			case 8:
			case 9:
				return board[2][index - 7];
			default:
				return '0';
		}
	}
	//Returns the 2D array containing player tokens
	public static char[][] ReturnBoard()
	{
		return board;
	}
}