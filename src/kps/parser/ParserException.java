package kps.parser;

/**
 * An extension of the Exception class to throw when errors occur
 * while parsing.
 * 
 * @author David
 *
 */
public class ParserException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new ParserException with the specified message.
	 * 
	 * @param message
	 * 		-- the error message to be displayed
	 */
	public ParserException(String message){
		super(message);
	}

}
