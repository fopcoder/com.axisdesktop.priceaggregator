package com.axisdesktop.priceaggregator.exception;

@SuppressWarnings( "serial" )
public class NoSuchEntityException extends Exception {

	public NoSuchEntityException() {
		super();
	}

	public NoSuchEntityException( String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace ) {
		super( message, cause, enableSuppression, writableStackTrace );
	}

	public NoSuchEntityException( String message, Throwable cause ) {
		super( message, cause );
	}

	public NoSuchEntityException( String message ) {
		super( message );
	}

	public NoSuchEntityException( Throwable cause ) {
		super( cause );
	}

}
