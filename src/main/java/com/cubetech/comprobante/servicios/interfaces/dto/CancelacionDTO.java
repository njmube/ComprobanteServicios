package com.cubetech.comprobante.servicios.interfaces.dto;

import java.util.List;

import lombok.Data;

@Data
public class CancelacionDTO {
	
	private List<Respuesta> resultado;
	
	@Data
	static public class Respuesta{
		private String uuid;
		private String codigo;
		private String mensaje;
	}
}
