package com.cubetech.comprobante.servicios.interfaces.dto;

import java.util.List;

import lombok.Data;

@Data
public class CancelaDTO {
	private String emisorCorrelacion;
	private List<String> uuid;
}
