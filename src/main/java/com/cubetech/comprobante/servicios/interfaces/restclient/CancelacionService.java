package com.cubetech.comprobante.servicios.interfaces.restclient;

import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteCancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteXmlDTO;

public interface CancelacionService {
	public ComprobanteCancelacionDTO cancelaComprobante(ComprobanteXmlDTO comprobante);
}
