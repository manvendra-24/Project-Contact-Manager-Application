package com.techlabs.exception;

public class AlreadyReportedException extends RuntimeException{
	private static final long serialVersionUID = 1L;

    public AlreadyReportedException(String message) {
    	super(message);
    }
}
