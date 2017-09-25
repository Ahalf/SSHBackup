//Adam Halfaker (ajh09g)

import java.util.Scanner;

class Reverse
{
	public static void main(String[] args)
	{
		long number = 0;
		do
		{
			System.out.println("Please enter a long integer (0 to exit)");
			Scanner input = new Scanner(System.in);
			number = input.nextLong();												//stores long 
			
			if (number != 0)														//only executes if input is not 0
			{
				System.out.println(reverseDigits(number));
			}
		} while(number != 0);
	}

	public static long reverseDigits(long n)
	{
		StringBuilder numberString = new StringBuilder();
		numberString.append(String.valueOf(n));																										
		numberString.reverse();
			
		if(numberString.charAt(0) != '0')									//if first char not 0 return value											 
		{
			return Long.valueOf(numberString.toString());									
		}
		while (numberString.charAt(0) == '0')								//deletes all leading zeroes then returns value
		{
			numberString.deleteCharAt(0);
		}
		return Long.valueOf(numberString.toString());			
	}
}