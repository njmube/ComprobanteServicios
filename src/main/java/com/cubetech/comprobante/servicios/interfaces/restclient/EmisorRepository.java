package com.cubetech.comprobante.servicios.interfaces.restclient;

import com.cubetech.comprobante.servicios.application.error.ConexionException;
import com.cubetech.comprobante.servicios.interfaces.dto.EmitirDTO;

public interface EmisorRepository {
	public EmitirDTO consultaEmisor(String cuenta, String correlacion) throws ConexionException;
}
