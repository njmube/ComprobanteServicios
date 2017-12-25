package com.cubetech.comprobante.servicios.application;

import java.util.List;

import com.cubetech.comprobante.servicios.domain.valueobject.SolicitudCancelacion;

public interface SolicitudCancelacionService {
	public SolicitudCancelacion cancelaComprobantes(String cuenta, String emisorCorrelacion, List<String> uuids);
}
