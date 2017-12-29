package com.cubetech.comprobante.servicios.interfaces.facade.impl;

import org.springframework.stereotype.Service;

import com.cubetech.comprobante.servicios.application.SolicitudCancelacionService;
import com.cubetech.comprobante.servicios.domain.valueobject.SolicitudCancelacion;
import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelaDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.facade.ComprobanteFacadeService;
import com.cubetech.comprobante.servicios.interfaces.facade.assembler.ComprobanteFacadeAssembler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComprobanteFacadeServiceImpl implements ComprobanteFacadeService {

	private SolicitudCancelacionService solicitudCancelacionService;
	private ComprobanteFacadeAssembler comprobanteFacadeAssembler;
	
	public ComprobanteFacadeServiceImpl(SolicitudCancelacionService solicitudCancelacionService){
		this.solicitudCancelacionService = solicitudCancelacionService;
		this.comprobanteFacadeAssembler = new ComprobanteFacadeAssembler();
	}
	
	@Override
	public CancelacionDTO cancelaComprobantes(String cuenta, CancelaDTO cancelaDto) {
		CancelacionDTO ret = null;
		SolicitudCancelacion solicitudCancelacion;
		
		try{
			solicitudCancelacion = this.solicitudCancelacionService.cancelaComprobantes(cuenta, cancelaDto.getEmisorCorrelacion(), cancelaDto.getUuid());
			ret = this.comprobanteFacadeAssembler.toCancelacionDTO(solicitudCancelacion);
		}catch(Exception e){
			log.error("Error al Cancelacer ", e);
			throw e;
		}
		return ret;
	}

	@Override
	public ArchivoDTO consultaArchivo(String cuenta, String uuid, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
