import java.util.Scanner;

/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Haochen Wang
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;
	
	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			//Prompt the user to enter the number of philosophers.
			Scanner keyboard = new Scanner(System.in);
			System.out.print("Enter the number of PHILOSOPHERS? ");
			//Read a String.
			String key = keyboard.nextLine();
			int iPhilosophers = 0;
			//If nothing's typed, then the given default is used.
			if(key.equals("")){
				iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
				System.out.print("No input!!-The default number," + DEFAULT_NUMBER_OF_PHILOSOPHERS + " is being applied!\n");
			}
			//Otherwise, change the string to an integer and go with that number.
			else
				iPhilosophers=Integer.parseInt(key);
			//If the number is negative, print the error message of that.
			if(iPhilosophers<0){
				System.out.println("\"" + iPhilosophers + "\"" + " is not a positive decimal integer!\n");
				System.out.println("Usage: java DiningPhilosophers [DEFAULT_NUMBER_OF_PHILOSOPHERS]");
				System.exit(1);
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
		//Catch the exception and handle it if the string can't be changed to an integer.
		catch(NumberFormatException e)
		{
			System.err.println(e.getMessage() + " is not a positive decimal integer!\n");
			System.err.println("Usage: java DiningPhilosophers [DEFAULT_NUMBER_OF_PHILOSOPHERS]");
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}
// EOF
