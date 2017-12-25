package com.cubetech.comprobante.servicios.interfaces.restclient;

import java.util.List;

import com.cubetech.comprobante.servicios.application.error.ConexionException;
import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;



public interface ArchivoRepository {
	public ArchivoDTO findbyCuentaCorrelacion(String cuenta, String correlation) throws ConexionException;
	public List<ArchivoDTO> save(String cuenta, List<ArchivoDTO> archivo);
	
}
