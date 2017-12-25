package com.cubetech.comprobante.servicios.application.error;

public abstract class ComprobanteErrorException extends RuntimeException {

	private static final long serialVersionUID = 380888672894557573L;

	public ComprobanteErrorException(){
		super();
	}
	public ComprobanteErrorException(String message){
		super(message);
	}
	public ComprobanteErrorException(String message, Throwable cause ){
		super(message, cause);
	}
	
	public ComprobanteErrorException(Throwable cause ){
		super(cause);
	}
	
	abstract public String getCode();
	
}
