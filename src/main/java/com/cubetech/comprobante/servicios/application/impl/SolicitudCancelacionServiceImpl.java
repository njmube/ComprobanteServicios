package com.cubetech.comprobante.servicios.application.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubetech.comprobante.servicios.application.CancelarService;
import com.cubetech.comprobante.servicios.application.EmisorService;
import com.cubetech.comprobante.servicios.application.SolicitudCancelacionService;
import com.cubetech.comprobante.servicios.application.assembler.ComprobanteAssembler;
import com.cubetech.comprobante.servicios.application.assembler.Utilerias;
import com.cubetech.comprobante.servicios.application.error.InternalException;
import com.cubetech.comprobante.servicios.application.error.ValidacionExcepcion;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.entidad.repository.ComprobanteRepository;
import com.cubetech.comprobante.servicios.domain.valueobject.CancelacionComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;
import com.cubetech.comprobante.servicios.domain.valueobject.EstadoCancelacion;
import com.cubetech.comprobante.servicios.domain.valueobject.EstadoComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Rfc;
import com.cubetech.comprobante.servicios.domain.valueobject.SolicitudCancelacion;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SolicitudCancelacionServiceImpl implements SolicitudCancelacionService {
	private EmisorService emisorService;
	private ComprobanteRepository comprobanteRepository;
	private ComprobanteAssembler comprobanteAssembler;
	private CancelarService cancelacionService;
	
	@Autowired
	public SolicitudCancelacionServiceImpl(EmisorService emisorService, CancelarService cancelacionService,
													ComprobanteRepository comprobanteRepository){
		this.emisorService = emisorService;
		this.cancelacionService = cancelacionService;
		this.comprobanteRepository = comprobanteRepository;
		this.comprobanteAssembler = new ComprobanteAssembler();
	}
	
	@Override
	public SolicitudCancelacion cancelaComprobantes(String cuenta, String emisorCorrelacion,
			List<String> uuids) {
		Emisor emisor = null;
		SolicitudCancelacion ret = new SolicitudCancelacion();
		List<Comprobante> comprobantes;
		List<CancelacionComprobante> cancelaciones;
		CancelacionComprobante cancelacionComprobante;
		
		log.info("Uniciando Solicitud de cancelacion UUID: {}", uuids);
		emisor = this.emisorService.consultaEmisor(cuenta, emisorCorrelacion);
		
		if(!emisor.isPosibleFirmar())
			throw new ValidacionExcepcion("Certificado vencido");
		
		comprobantes = consultaComprobantes(emisor.getRfc(), uuids);
		cancelaciones = new ArrayList<>();
		for(Comprobante comprobante : comprobantes){
			try{
				if(comprobante.getEstado() == EstadoComprobante.CANCELADO){
					cancelacionComprobante = new CancelacionComprobante();
					cancelacionComprobante.setComprobante(comprobante);
					cancelacionComprobante.setEstado(EstadoCancelacion.CANCELADO);
				}else{
					cancelacionComprobante = this.cancelacionService.cancelaComprobante(comprobante, emisor);
				}
			}catch(InternalException e){
				log.error("Error al cancelar ", e);
				cancelacionComprobante = new CancelacionComprobante();
				cancelacionComprobante.setComprobante(comprobante);
				cancelacionComprobante.setEstado(EstadoCancelacion.NO_VALIDO);
			}
			cancelaciones.add(cancelacionComprobante);
		}
		ret.setCancelaciones(cancelaciones);
		log.debug("Respuesta SolicitudCancelacion {}", ret);
		log.info("Solicitud de cancelacion completada");
		return ret;
	}
	
	private List<Comprobante> consultaComprobantes(Rfc rfc, List<String> uuids){
		List<Comprobante> ret = null;
		Comprobante comprobante;
		
		if(Utilerias.existeInfo(uuids)){
			ret = new ArrayList<>();
			for(String tmp : uuids){
				comprobante = this.comprobanteRepository.findOneByUuid_Valor(tmp);
				if(comprobante == null){
					ret.add(this.comprobanteAssembler.toComprobante(tmp));
				}else
					ret.add(comprobante);
			}
		}
		return ret;
	}

}
