import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Haochen Wang
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	//A given philosopher strats eating and after a random interval finishes eating.
	public void eat()
	{
		try
		{
			System.out.println("Finally, a given phil(" + getTID() + ") has started EATING!!");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("Finally, a given phil(" + getTID() + ") has finished EATING!!!!");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	//A given philosopher strats thinking and after a random interval finishes thinking.
	public void think()
	{
		try
		{
			System.out.println("A given phil(" + getTID() + ") has started thinking.");
			yield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			yield();
			System.out.println("A given phil(" + getTID() + ") has finished thinking.");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */
	//A given philosopher strats talking and after a random interval finishes talking.
	public void talk()
	{
		System.out.println("\nA given phil(" + getTID() + ") has started TALKING.");
		yield();
		saySomething();
		yield();
		System.out.println("A given phil(" + getTID() + ") has finished TALKING.\n");
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			DiningPhilosophers.soMonitor.pickUp(getTID());

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID());

			think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			//A philosopher decides whether he or she wants to say something.
			boolean[] wannaTalk = new boolean[2];
			wannaTalk[0] = false;
			wannaTalk[1] = true;
			//Randomly generates 'true' or 'false' to decide to say something or not, using an array.
			int t = (int)(Math.random()*2);
			if(wannaTalk[t] == true)//if 'true', then talk.
			{
				//Check if there is someone who is talking currently.
				DiningPhilosophers.soMonitor.requestTalk(getTID());
				
				talk();
				//Notice others that the given one finished talking.
				DiningPhilosophers.soMonitor.endTalk(getTID());
			}

			yield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF
