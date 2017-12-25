package com.cubetech.comprobante.servicios.application.error;

public class TimbradoException extends ComprobanteErrorException {

	private static final long serialVersionUID = -8565908383407912532L;
	private static final String GROUP = "05"; 
	
	public TimbradoException(){
		super();
	}
	public TimbradoException(String message){
		super(message);
	}
	public TimbradoException(String message, Throwable cause ){
		super(message, cause);
	}
	
	public TimbradoException(Throwable cause ){
		super(cause);
	}
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return GROUP;
	}

}
