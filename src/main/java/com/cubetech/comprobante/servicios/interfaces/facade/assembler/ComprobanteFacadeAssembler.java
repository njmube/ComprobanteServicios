package com.cubetech.comprobante.servicios.interfaces.facade.assembler;

import java.util.ArrayList;
import java.util.List;

import com.cubetech.comprobante.servicios.application.assembler.Utilerias;
import com.cubetech.comprobante.servicios.domain.valueobject.CancelacionComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.SolicitudCancelacion;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelacionDTO;


public class ComprobanteFacadeAssembler {
	public CancelacionDTO toCancelacionDTO(SolicitudCancelacion solicitud){
		CancelacionDTO ret = null;
		List<CancelacionComprobante> cancelaciones = solicitud.getCancelaciones();
		
		if(Utilerias.existeInfo(cancelaciones)){
			ret = new CancelacionDTO();
			ret.setResultado(new ArrayList<>());
			for(CancelacionComprobante tmp : cancelaciones){
				ret.getResultado().add(toCancelacionRespuesta(tmp));
			}
		}
		
		return ret;
	}
	
	public CancelacionDTO.Respuesta toCancelacionRespuesta(CancelacionComprobante cancelacionComprobante){
		CancelacionDTO.Respuesta ret = null;
		
		if(cancelacionComprobante != null){
			ret = new CancelacionDTO.Respuesta();
			ret.setCodigo(String.valueOf(cancelacionComprobante.getEstado().getCodigo()));
			ret.setUuid(cancelacionComprobante.getComprobante().getUuid().getvalor());
			ret.setMensaje(cancelacionComprobante.getEstado().getDescripcion());
		}
		
		return ret;
	}
}
