package com.cubetech.comprobante.servicios.application.error;



public class ConexionException extends ComprobanteErrorException {

	private static final long serialVersionUID = -9216375161588752544L;
	private static final String GROUP = "01";

	public ConexionException(String msg) {
		super(msg);
	
	}

	public ConexionException(String message, Throwable cause ){
		super(message, cause);
	}
	
	public String getCode(){
		return GROUP;
	}

}
