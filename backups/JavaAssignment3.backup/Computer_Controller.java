import java.util.Scanner;
import java.util.Random;


public class Computer_Controller extends Controller
{
    protected int [] positions = {1,2,3,4,5,6,7,8,9};                   //will be used to restrict the values choses by RNG

    public Computer_Controller(String c, String p)
    {
        super(c,p);                                                     //computer and one player
    }
    public Computer_Controller(String c)
    {

        super(c);                                                       //two computers 

    }
    public int Block(char[][] gameBoard, int turn)
    {

        int rowBlock = 0;
        int colBlock = 0;
        int diagBlock = 0;

        rowBlock = Find_Row_Blocks(gameBoard, turn);                    //stores the position needed to block the opponent
        colBlock = Find_Col_Blocks(gameBoard, turn);                    //*
        diagBlock = Find_Diag_Blocks(gameBoard, turn);                  //*

        if(rowBlock > 0)                                                //0 is returned if no blocks needed
        {
            return rowBlock;
        }
        else if(colBlock > 0)
        {
            return colBlock;
        }
        else if(diagBlock > 0)
        {
            return diagBlock;
        }
        return 0;                                                           //if 0 is returned by Block function, no blocks needed
    }
    public static int CenterOpen(char[][] gameBoard)                        
    {                                                                          //returns Center position if open
        if(gameBoard[1][1] == ' ') 
            return 5;
        return 0;
    }
    public int GetPosition(Board b, int turn)
    {                                                               //override the GetPosition function from Controller. Values given to
                                                                    // UpdateBoard function in the main program
        int choice = 0;                                             //stores position for win, block, or random position
        int index = 0;
        if(Win(b.ReturnBoard(), turn) != 0)
        {
            choice = Win(b.ReturnBoard(), turn);
            return choice;
        }
        else if(Block(b.ReturnBoard(), turn) != 0)
        {
            choice = Block(b.ReturnBoard(), turn);
            
        //*  Resizes the position array used by RNG ***************************************
            int[] temp = new int [positions.length-1];
            index = 0;
                //System.out.println("Positions.length = " + positions.length);
            for(int i = 0; i < positions.length; i++)
            {
                if(choice != positions[i])
                {
                    temp[index] = positions[i];
                        //System.out.println("Positions[i] = "+positions[i]);
                    ++index;
                }
            }
            positions = new int[temp.length];
            System.arraycopy(temp, 0, positions, 0, positions.length);
        //**********************************************************************************

            return choice;
        }

        else if(CenterOpen(b.ReturnBoard()) != 0)                           
        {
            choice = CenterOpen(b.ReturnBoard());
            System.out.println("In CenterOpen");
           
        //*  Resizes the position array used by RNG ***************************************
            int[] temp = new int [positions.length-1];
            index = 0;
            for(int i = 0; i < positions.length; i++)
            {
                if(choice != positions[i])
                {
                    temp[index] = positions[i];
                    ++index;
                }
            }
            positions = new int[temp.length];
            System.arraycopy(temp, 0, positions, 0, positions.length);
        //**********************************************************************************

            return choice;
        }
        else
        {                                                                           //for all other moves

            Random computer_position = new Random();
            choice = computer_position.nextInt(positions.length);                   //stores random, available position in choice

        //Resizes the position array used by RNG ***************************************
            int[] temp = new int [positions.length-1];

            choice = positions[choice];
            index = 0;
           
            for(int i = 0; i < positions.length; i++)
            {
                if(choice != positions[i])
                {
                    temp[index] = positions[i];
                    
                    ++index;
                }

            }
            positions = new int[temp.length];
            System.arraycopy(temp, 0, positions, 0, positions.length);

            return choice;

        }       
    }
    
    //Handles computer wins. Will return the winning move for the computer
    public int Win(char[][]gameBoard, int turn)
    {
        int player = turn;                                          //Will subtract 1 from player to essentially get the blocks necessary. 
        if(Find_Diag_Blocks(gameBoard, player+1) > 0)               //If there is a block that means there are two tokens in a row, which is
        {                                                           //is also a win. So if a # > 0 is returned, this is a winning move for the current player
            return Find_Diag_Blocks(gameBoard, player+1);
        }

        else if (Find_Col_Blocks(gameBoard, player+1) > 0)
        {
            return Find_Col_Blocks(gameBoard, player+1);
        }
        
        else if (Find_Row_Blocks(gameBoard, player+1) > 0)
        {
            return Find_Row_Blocks(gameBoard, player+1);
        }
        return 0;
    }

    // hard coded possible blocks. This does not work 100% of the time. Don't know why!
    public int Find_Diag_Blocks(char [][]gameBoard, int turn)
    {
        boolean CheckLowerRight = (gameBoard[2][2] == ' ');
        boolean CheckLowerLeft = (gameBoard[2][0] == ' ');
        boolean CheckUpperLeft = (gameBoard[0][0] == ' ');
        boolean CheckUpperRight = (gameBoard[0][2] == ' ');

        if (super.GetPlayer(turn) == 'O')                                   //checks to see which player to check Diag blocks for
        {
            if(gameBoard[0][0] == 'X' && gameBoard[1][1] == 'X')
            {
                if(gameBoard[2][2] == ' ')
                    return 9;
            }
            else if(gameBoard[1][1] == 'X' && gameBoard[2][2] == 'X')
            {
                if(gameBoard[0][0] == ' ')
                    return 1;
            }
            else if (gameBoard[0][2] == 'X' && gameBoard[1][1] == 'X')
            {
                if(gameBoard[2][0] == ' ')
                    return 7;
            }
            else if (gameBoard[2][0] == 'X' && gameBoard[1][1] =='X')
            {
                if(gameBoard[0][2] == ' ')
                    return 3;
            }
        }

        else  
        {
            if(gameBoard[0][0] == 'O' && gameBoard[1][1] == 'O')
            {
                if(gameBoard[2][2] == ' ')
                    return 9;
            }
            else if(gameBoard[1][1] == 'O' && gameBoard[2][2] == 'O')
            {
                if(gameBoard[0][0] == ' ')
                    return 1;
            }
            else if (gameBoard[0][2] == 'O' && gameBoard[1][1] == 'O')
            {
                if(gameBoard[2][0] == ' ')
                    return 7;
            }
            else if (gameBoard[2][0] == 'O' && gameBoard[1][1] =='O')
            {
                if(gameBoard[0][2] == ' ')
                    return 3;
            }
        }
        return 0;
    }

    //similar to the Find_Wins function in Controller. loops through columns looking for two tokens in the column
    public int Find_Col_Blocks(char [][]gameBoard, int turn)
    {   
        int colO = 0;
        int colX = 0;
        int block = 2;
        int total = 0;
        int open_i_position = 0, open_j_position = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(gameBoard[j][i] == 'X')
                    colX++;

                if(gameBoard[j][i] == ' ')
                {
                    open_i_position = i;
                    open_j_position = j;
                }

                if(gameBoard[j][i] == 'O')
                    colO++;
            }
            if(colX - colO == block && super.GetPlayer(turn) == 'O')
                return Switch_for_Positions(open_j_position, open_i_position);
            
            if(colO - colX == block && super.GetPlayer(turn) == 'X')
                return Switch_for_Positions(open_j_position, open_i_position);

            colO = 0;
            colX = 0;
        }
        return 0;


    }

    //same as Find_Columns
    public int Find_Row_Blocks(char[][] gameBoard, int turn)
    {
        int rowO = 0;
        int rowX = 0;
        int block = 2;
        int total = 0;
        int open_i_position = 0, open_j_position = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(gameBoard[i][j] == 'X')
                    rowX++;
                
                if(gameBoard[i][j] == ' ')
                {
                    open_i_position = i;
                    open_j_position = j;
                }

                if(gameBoard[i][j] == 'O')
                    rowO++;
            }
            if(rowX - rowO == block && super.GetPlayer(turn) == 'O')
                return Switch_for_Positions(open_i_position, open_j_position);
            
            if(rowO - rowX == block && super.GetPlayer(turn) == 'X')
                return Switch_for_Positions(open_i_position, open_j_position);

            rowO = 0;
            rowX = 0;
        }
        return 0;
    }
    public int Switch_for_Positions(int openPosition_i, int openPosition_j)
    {
        switch(openPosition_i)
        {
            case 0:
            return openPosition_j + 1;

            case 1:
            return openPosition_j + 4;

            case 2:
            return openPosition_j + 7;

            default:
            break;
        }
        return 0;
    }
};