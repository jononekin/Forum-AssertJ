package exceptions;


public class PurchaseClosedException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	 public PurchaseClosedException()
	  {
	    super();
	  }
	  /**This exception is triggered if the purchase is closed 
	  *@param s String of the exception
	  */
	  public PurchaseClosedException(String s)
	  {
	    super(s);
	  }
	}