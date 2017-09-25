import java.util.Scanner;

public class Human_Controller extends Computer_Controller
{
	public Human_Controller(String c, String p)
	{
        super(c,p);             //computer and one player
    }
    
    public int GetPosition(Board b, int turn)							
    {																			//overrides GetPosition from parent
    	int choice = 0;
    	if(player.equals("1"))													//member data, stores command line input
    	{
    		if(turn % 2 == 0)													//Computer 1st player
    			return super.GetPosition(b, turn);
    		else
    		{
    			choice= Human_Turn(b, turn);
    			
    			//Resizing positions array******************************
    			int index = 0;
    			int[] temp = new int [positions.length-1];
    			System.out.println(positions.length);
    			for(int i = 0; i < positions.length; i++)
    			{
    				if(choice != positions[i])
    				{
    					temp[index] = positions[i];
    					System.out.println(temp[index]);
    					++index;
    				}

    			}
    			positions = new int[temp.length];
    			System.arraycopy(temp, 0, positions, 0, positions.length);
    			
    			return choice;

    		}
    		

    	}
    	else
    	{																		//Human player 1
    		if(turn%2 == 0)
    		{
    			choice= Human_Turn(b, turn);
    			//** RESIZE ARRAY **********************************
    			int index = 0;
    			int[] temp = new int [positions.length-1];

    			for(int i = 0; i < positions.length; i++)
    			{
    				if(choice != positions[i])
    				{
    					temp[index] = positions[i];
    					System.out.println(index);
    					++index;
    				}

    			}
    			positions = new int[temp.length];
    			System.arraycopy(temp, 0, positions, 0, positions.length);

    			return choice;
    		}
    		else
    			return super.GetPosition(b,turn);								//computer turn
    	}


    }

    // Same method as GetPosition in Controller.java but I don't think I can call that function from this class.
    // Handles human input and returns their selected position
    public int Human_Turn(Board b, int turn)
    {					
    	int playerNumber = 2;
    	if (turn%2 == 0)
    		playerNumber = 1;
    	System.out.print("Player " + playerNumber);
    	System.out.print(" Enter Position: ");
    	Scanner i = new Scanner(System.in);
    	String input = i.nextLine();
		while (b.GetBoardState(Integer.valueOf(input)) != ' ')		//accounts for positions already taken
		{
			System.out.print("\nPosition taken. \n");
			System.out.print("Enter Position: ");
			i = new Scanner(System.in);
			input = i.nextLine();
			
		}	 

		return Integer.valueOf(input);
	}
}