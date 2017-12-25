package com.cubetech.comprobante.servicios.application.error;

public class ConvertirException extends ComprobanteErrorException {

	private static final long serialVersionUID = -1323985740980964130L;
	private static final String GROUP = "03";
	
	public ConvertirException(String msg) {
		super(msg);
	}

	public ConvertirException(String message, Throwable cause ){
		super(message, cause);
	}
	
	public String getCode(){
		return GROUP;
	}
	
	
}
