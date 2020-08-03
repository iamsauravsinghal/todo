package com.saurav.exception;

public class DeleteFailedException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteFailedException(String message) {
		super(message);
	}
}
