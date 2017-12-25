package com.cubetech.comprobante.servicios.application.error;


public class ValidacionExcepcion extends ComprobanteErrorException {

	private static final long serialVersionUID = 2183619299900358226L;
	private static final String GROUP = "06";  
	
	public ValidacionExcepcion(){
		super();
	}
	public ValidacionExcepcion(String message){
		super(message);
	}
	public ValidacionExcepcion(String message, Throwable cause ){
		super(message, cause);
	}
	
	public ValidacionExcepcion(Throwable cause ){
		super(cause);
	}
	
	public String getCode(){
		return GROUP;
	}
}
