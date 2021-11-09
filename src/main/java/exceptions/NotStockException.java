package exceptions;

public class NotStockException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	 public NotStockException()
	  {
	    super();
	  }
	  /**This exception is triggered if the purchase is closed 
	  *@param s String of the exception
	  */
	  public NotStockException(String s)
	  {
	    super(s);
	  }
	}