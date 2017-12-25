package com.cubetech.comprobante.servicios.interfaces.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="acuse")

public class ComprobanteCancelacionDTO {
	private String status;
	private String message;
	private String acuse;
	private String uuid;
	private String uuidStatus;
}
