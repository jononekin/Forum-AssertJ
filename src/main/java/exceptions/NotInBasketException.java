package exceptions;

public class NotInBasketException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	 public NotInBasketException()
	  {
	    super();
	  }
	  /**This exception is triggered if the purchase is closed 
	  *@param s String of the exception
	  */
	  public NotInBasketException(String s)
	  {
	    super(s);
	  }
	}