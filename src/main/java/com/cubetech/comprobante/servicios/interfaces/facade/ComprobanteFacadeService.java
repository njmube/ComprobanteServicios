package com.cubetech.comprobante.servicios.interfaces.facade;

import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelaDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelacionDTO;

public interface ComprobanteFacadeService {
	public CancelacionDTO cancelaComprobantes(String cuenta, CancelaDTO cancelaDto);
	public ArchivoDTO consultaArchivo(String cuenta, String uuid, String type);
}
