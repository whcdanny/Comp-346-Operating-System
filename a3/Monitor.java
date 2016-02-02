/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Haochen Wang
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	//Declare all variables that I need.
	int[] state;
	boolean[] usefork;//Each philosopher's fork
	//States for an int array
	private static final int THINKING = 0;
	private static final int HUNGRY = 1;
	private static final int EATING = 2;
	private static final int TALKING = 4;
	private static final int WAITING = 5;
	private static int t = 0;
	

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state = new int[piNumberOfPhilosophers];//Set the number of the philosophers' state.
		usefork = new boolean[piNumberOfPhilosophers];//Set the number of the philosophers' forks.
		//Initialize each state to 'THINKING' and fork to 'false'
		for (int i=0;i<piNumberOfPhilosophers;i++) {
			state[i] = THINKING;
			usefork[i] = false;//Not in use
		}

	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */
	//Test method to see if a given phil. can eat or not.
	public synchronized void test(final int i)
	{
		//A given phil. can eat only if both side phils. are not eating and both forks are available.
		//Only if both forks are available, he or she can pick up two forks.
		if (!usefork[i] && !usefork[(i+1) % state.length]
		                            && state[i] == HUNGRY 
		                            && state[(i+(state.length-1)) % state.length] != EATING
		                            && state[(i+1) % state.length] != EATING)
		{
			//Both forks are in use now
			usefork[i]=true;
			usefork[(i+1) % state.length]=true;
			state[i] = EATING;//Changes to 'EATING' state.
		}
	}
	
	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		int i = piTID-1;
		state[i] = HUNGRY;//Say I am 'HUNGRY'.
		//Test if the condition to eat is satisfied or not.
		test(i);
		//If a phil. gets refused to eat, then wait.
		if (state[i] != EATING){
			try 
			{
				wait();
			} 
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		int i = piTID-1;
		//After eating, it sets the state of the phil. to 'THINKING'. 
		state[i] = THINKING;
		//Then it sets the two forks to available.
		usefork[i] = false;
		usefork[(i+1) % state.length]=false;
		//Give a notice both side phils. that the forks are available currently.
		test((i+(state.length-1)) % state.length);
		if (state[(i+(state.length-1)) % state.length] == EATING){
			notify();//If the left side phil. can eat, then go eat.
		}

		test((i+1) % state.length);
		if (state[(i+1) % state.length] == EATING){
			notify();//If the right side phil. can eat, then go eat.
		}
	}

	//Test if a given phil. can talk or not.
	public synchronized void testTalk(final int i)
	{
		t=0;
		//See if there is anyone talking currently.
		while(t<state.length && state[t] != TALKING){
			t=t+1;
		}
		//If nobodies are talking now, then a phil. can talk.
		if(t==state.length){
			state[i]=TALKING;//Set the state of a given phil. to 'TALKING'.
		}
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	//There are two tests, one of which is for checking if someone is 'WAITING' and the other is for testing if they can finally talk or not.
	public synchronized void requestTalk(final int piTID)
	{
		int i = piTID-1;
		//See if there is anyone waiting to talk.
		waitTest(i);
		//After waiting, test if a phil. can talk now.
		testTalk(i);
		//If a phil. get refused to talk, then wait.
		if(state[i] != TALKING){
			try 
			{
				//set the state to 'WAITING'.
				state[i]=WAITING;
				wait();
				//Finally, it can talk now.
				state[i]=TALKING;//Set to 'TALKING'.
				notify();//Let the phils. go for the final test.
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk(final int piTID)
	{
		int i = piTID-1;
		//After talking, set the state of the  phil. to 'THINKING'.
		state[i]=THINKING;
		//Test if anyone can talk or not.
		testTalk(i);
		if(t==state.length){
			//Return the state of the testing phil. to the original state, which is actually finished talking.
			state[i]=THINKING;
			notify();//If anyone can talk, then go talk.
		}
	}
	//A method to see if there are other phils. who are in 'WAITING' state or not before it does the final test.
	public synchronized void waitTest(final int i){
		t=0;
		//Test if there are someone in state, 'WAITING'.
		while(t<state.length && state[t] != WAITING){
			t=t+1;
		}
		//If there are someone waiting, it also has to wait.
		if(t!=state.length){
			try 
			{
				wait();
			} 
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
// EOF
