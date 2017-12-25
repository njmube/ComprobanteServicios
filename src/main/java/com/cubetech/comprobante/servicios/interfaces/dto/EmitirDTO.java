package com.cubetech.comprobante.servicios.interfaces.dto;

import lombok.Data;

@Data
public class EmitirDTO {
	private String noCertificado;
	private String certificado;
	private String lugarExpedicion;
	private String rfc;
	private String nombre;
	private String regimenFiscal;
	private String pass;
	private String privadoId;
	private boolean emitir;
}
