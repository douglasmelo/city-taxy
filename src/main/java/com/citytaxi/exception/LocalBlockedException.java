package com.citytaxi.exception;

public class LocalBlockedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7430850567192486144L;

	public LocalBlockedException(){
		super();
	}
	
	public LocalBlockedException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public LocalBlockedException(String message) {
		super(message);
	}
}
