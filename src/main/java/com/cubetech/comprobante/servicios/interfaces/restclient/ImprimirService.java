package com.cubetech.comprobante.servicios.interfaces.restclient;

import com.cubetech.comprobante.servicios.interfaces.dto.ImpresionDTO;

public interface ImprimirService {
	public void representacionImpresa(ImpresionDTO comprobante, String cuenta);
}
