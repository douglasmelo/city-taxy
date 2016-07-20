package com.citytaxi.exception;

public class MapNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3128320069478392367L;

	public MapNotFoundException(){
		super();
	}
	
	public MapNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}
	
}
