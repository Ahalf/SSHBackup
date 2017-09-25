//Adam Halfaker (ajh09g)

import java.util.Random;
import java.util.Scanner;

class DiceStats
{
	public static void main(String[] args)
	{
		Dicestats();
	}

	public static void Dicestats()
	{
		
		Scanner input = new Scanner(System.in);
		Random roll = new Random();
		int numberOfDice = 0;
		int numberOfRolls = 0;
		int sum = 0;

		System.out.print("How many dice will constitute one roll? ");
		numberOfDice = input.nextInt();
		System.out.println();

		int lowerBound = numberOfDice;
		int upperBound = 6 * numberOfDice;

		
		int[] countArray = new int[upperBound - lowerBound + 1]; 	//will store the number of times a value is rolled

		
		System.out.print("How many rolls? ");
		numberOfRolls = input.nextInt();
		
		for(int i = 0; i < numberOfRolls; i++)					
		{
			for (int j = 0; j < numberOfDice; j++)					//adds the total for each dice thrown in one roll
			{
				int result = roll.nextInt(6) + 1;
				sum += result;										//stores results in sum
			}
			countArray[sum - lowerBound] += 1;						//increments value in array for the sum

			sum = 0;	
		}

		//results table
		System.out.println("Sum        # of times    Percentage");
		for (int i = 0; i < countArray.length; i++)
		{	
			double average = (double)countArray[i] / numberOfRolls * 100;
			System.out.printf("%-10d %-8d      %-5.2f %% \n", i + lowerBound, countArray[i], average);
		}

		

	}
}