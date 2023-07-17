package br.eti.freitas.startproject.infrastructure.error;

/**
* Contains default constant messages
*
* @author  Roberto Freitas
* @version 1.0
* @since   2023-03-01
*/
public class ConstantError {


	/**
	 * Default constructor
	 *
	 */
	public ConstantError() {
	}

	// ***********************************************
	// Constants
	// ***********************************************

	/**
	 * contains values used to display informational messages from the security system
	 */
	
	public static String NOT_FOUND = "Not found";
	public static String NOT_EXISTS = "Could not find %x with value %x";
	public static String NOT_DELETE = "Could not delete resource %x with identifier %x";
	public static String USER_DISABLED = "User is disabled";
	public static String USER_NOT_FOUND = "User not found with username or email %x";
	public static String INVALID_CREDENTIALS = "Invalid credentials";


}
