package com.cubetech.comprobante.servicios.application;

import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

public interface EmisorService {
	public Emisor consultaEmisor(String cuenta, String correlacion);
}
