package com.utfpr.delivery.exception;

/*@ResponseStatus(code = HttpStatus.NOT_FOUND)*/
public class NotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String message) {
		super(message);
	}
}	
