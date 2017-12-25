package com.cubetech.comprobante.servicios.application.error;


public class ConfirmacionException extends ComprobanteErrorException {
	
	private static final long serialVersionUID = 8837237958545903743L;
	private static final String GROUP = "02";  
	
	public ConfirmacionException(){
		super();
	}
	public ConfirmacionException(String message){
		super(message);
	}
	public ConfirmacionException(String message, Throwable cause ){
		super(message, cause);
	}
	
	public ConfirmacionException(Throwable cause ){
		super(cause);
	}
	
	public String getCode(){
		return GROUP;
	}

}
