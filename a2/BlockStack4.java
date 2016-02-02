/**
 * Class BlockStack
 * Implements character block stack and operations upon it.
 *
 * @author Haochen Wang;
 */
class BlockStack
{
	/**
	 * # of letters in the English alphabet + 2
	 */
	public static final int MAX_SIZE = 28;

	/**
	 * Default stack size
	 */
	public static final int DEFAULT_SIZE = 6;

	/**
	 * Current size of the stack
	 */
	private int iSize = DEFAULT_SIZE;

	/**
	 * Current top of the stack
	 */
	private int iTop  = 3;
	/**
	 * stack counter
	 */
	private int sctr=0;

	/**
	 * stack[0:5] with four defined values
	 */
	private char acStack[] = new char[] {'a', 'b', 'c', 'd', '$', '$'};

	/**
	 * Default constructor
	 */
	public BlockStack()
	{
	}

	public int getiSize() {
		
		
		return iSize;
	}

	public void setiSize(int iSize) {
		this.iSize = iSize;
	}

	public int getiTop() {
		return iTop;
	}

	public void setiTop(int iTop) {
		this.iTop = iTop;
	}

	public int getSctr() {
		return sctr;
	}

	public void setSctr(int sctr) {
		this.sctr = sctr;
	}

	public char[] getAcStack() {
		return acStack;
	}
	public char getAcStackatPos(int i) {
		return acStack[i];
	}

	public void setAcStack(char[] acStack) {
		this.acStack = acStack;
	}
	public void setAcStackatPOs(int i, char ch) {
		this.acStack[i] = ch;
	}

	/**
	 * Supplied size
	 */
	// modifeid so far
	public BlockStack(final int piSize)
	{


                if(piSize != DEFAULT_SIZE)
		{
			this.setAcStack(new char[piSize]);

			// Fill in with letters of the alphabet and keep
			// 2 free blocks
			for(int i = 0; i < piSize - 2; i++)
			//	this.acStack[i] = (char)('a' + i);
				// modified
			this.setAcStackatPOs(i,(char)('a' + i)) ;

		//	this.acStack[piSize - 2] = this.acStack[piSize - 1] = '$';
			//modified
			this.setAcStackatPOs(piSize - 2,'$');
			this.setAcStackatPOs(piSize - 1,'$');


			this.setiTop(piSize - 3) ;
                        this.setiSize(piSize);
                        this.setSctr(0);
		}
	}

	/**
	 * Picks a value from the top without modifying the stack
	 * @return top element of the stack, char
	 */
	// modified
	public char pick()
	{     //  sctr++;
	      setSctr(getSctr()+1);
		//return this.acStack[this.iTop];
		return this.getAcStackatPos(this.getITop());
		
		
	}

	/**
	 * Returns arbitrary value from the stack array
	 * @return the element, char
	 */
	// modified
	public char getAt(final int piPosition)
	{
		//sctr++;
		 setSctr(getSctr()+1);
		//return this.acStack[piPosition];
		 return this.getAcStackatPos(piPosition);
	}

	/**
	 * Standard push operation
	 */
	// modified
	public void push(final char pcBlock) throws pushexception
	{
		if (this.getITop()==this.getISize()-3)
			{
			throw new pushexception();
			}
		//sctr++;
		 setSctr(getSctr()+1);
		
		if (this.getITop()==-1)
		{
			this.setiTop(this.getITop()+1);
			this.setAcStackatPOs(this.getITop(),'a');
					
		}
		else
		//this.acStack[++this.iTop] = pcBlock;
		{
			this.setAcStackatPOs(this.getITop()+1,pcBlock);
			this.setiTop(this.getITop()+1);
		}
	}

	/**
	 * Standard pop operation
	 * @return ex-top element of the stack, char
	 */
	
	// modified
	public char pop() throws popexception
	{
		 setSctr(getSctr()+1);
		if (this.getITop()==-1)
			throw new popexception();
		//sctr++;
		
	//	char cBlock = this.acStack[this.iTop];
		char cBlock =getAcStackatPos(this.getITop());
		//this.acStack[this.iTop--] = '$'; // Leave prev. value undefined
		setAcStackatPOs(this.getITop(),'$');
		this.setiTop(this.getITop()-1);
		return cBlock;
	}
	
	
	// modified
	public int getITop(){
	//	sctr++;
		 setSctr(getSctr()+1);

		return iTop;
	}
	
	//modified
	public int getISize(){
		//sctr++;
		 setSctr(getSctr()+1);

		return iSize;
	}
	//modified
	public int getAccessCounter(){
		 

		return getSctr();
	}
	// modified
	public boolean isEmpty()
	{
	return (this.getITop() == -1);
	}
	
	
}

// EOF
