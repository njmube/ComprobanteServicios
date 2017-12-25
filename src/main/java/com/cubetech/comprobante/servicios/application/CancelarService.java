package com.cubetech.comprobante.servicios.application;

import com.cubetech.comprobante.servicios.application.xml.cancelacion.Cancelacion;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.CancelacionComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

public interface CancelarService {
	public CancelacionComprobante cancelaComprobante(Comprobante comprobante, Emisor emisor);
	public String generaXMl(Cancelacion cancelacion);
}
