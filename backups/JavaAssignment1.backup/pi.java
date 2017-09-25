//Adam Halfaker(ajh09g)
import java.util.Scanner;

class Pi
{
	public static void main (String[] args)
	{
		System.out.print("Compute to how many terms of the series: ");
		Pi();
	}

	public static void Pi ()
	{
		Scanner input = new Scanner(System.in);
		int terms = input.nextInt();
		double denominator = 1;
		double sum = 0;

		System.out.println("Terms          PI approximation");
		for (int i = 0; i < terms; i ++)
		{
			if (i % 2 == 0)														//if and else will ensure the add/sub signs change
			{
				sum += 4/denominator;
			}
			else
			{
				sum -= 4/denominator;
			}
			
			denominator += 2;													//keeps denominator odd

			System.out.printf("%-10d %12.6f \n", i + 1, sum);
		}
	}
}