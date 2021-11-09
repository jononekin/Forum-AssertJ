package exceptions;


public class NotEnoughtQuantityException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	 public NotEnoughtQuantityException()
	  {
	    super();
	  }
	  /**This exception is triggered if the purchase is closed 
	  *@param s String of the exception
	  */
	  public NotEnoughtQuantityException(String s)
	  {
	    super(s);
	  }
	}
