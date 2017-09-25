class TicTacToe
{
	public static void main(String[] args)
	{
		Board theBoard = new Board();								//initializes a new board
		int argsLength = args.length;								//length of command line args
		Controller theController;									//declares a Controller object
		
		//two humans
		if (argsLength == 0)
			theController = new Controller();						//if two humans, Controller class is initialized
		//two computers
		else if (argsLength == 1) 
			theController = new Computer_Controller(args[0]);		//if two computers, Computer_Controller class initialized
		
		//1 human/ 1 computer
		else if (argsLength == 2)									//if one human one computer, Human_Contoller initialized
		{
			theController = new Human_Controller(args[0], args[1]);
		}
		else														//for invalid input
		{
			System.out.println("Invalid input!");
			theController = new Controller();
		}
		int turn = 0;												//tracks number of plays and is used to determine player token

		theBoard.PrintBoard();									
		while (turn < 9)
		{
			
			int position = theController.GetPosition(theBoard, turn);		//gets the position inputed, return from the controller

			theBoard.UpdateBoard(position, theController.GetPlayer(turn));	// updates the board at that position
			theBoard.PrintBoard();											//prints updated board
			if(theController.Win_or_lose(theBoard.ReturnBoard(), turn))		//checks for wins and ties, breaks out if win before turns = 9
				break;
			turn++;	
		}
		
			
	}
}