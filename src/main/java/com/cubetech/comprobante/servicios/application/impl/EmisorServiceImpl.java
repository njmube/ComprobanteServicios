package com.cubetech.comprobante.servicios.application.impl;

import org.springframework.stereotype.Service;

import com.cubetech.comprobante.servicios.application.EmisorService;
import com.cubetech.comprobante.servicios.application.assembler.EmisorAssembler;
import com.cubetech.comprobante.servicios.application.error.ValidacionExcepcion;
import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.EmitirDTO;
import com.cubetech.comprobante.servicios.interfaces.restclient.ArchivoRepository;
import com.cubetech.comprobante.servicios.interfaces.restclient.EmisorRepository;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

@Service
public class EmisorServiceImpl implements EmisorService {

	private EmisorRepository emisorRepository;
	private ArchivoRepository archivoRepository;
	private EmisorAssembler emisorAssembler;
	
	public EmisorServiceImpl(EmisorRepository emisorRepository, ArchivoRepository archivoRepository){
		this.emisorRepository = emisorRepository;
		this.archivoRepository = archivoRepository;
		this.emisorAssembler = new EmisorAssembler();
	}
	
	@Override
	public Emisor consultaEmisor(String cuenta, String correlacion) {
		EmitirDTO emitir;
		ArchivoDTO publico;
		ArchivoDTO privado;
		Emisor ret = null;
		
		emitir = this.emisorRepository.consultaEmisor(cuenta, correlacion);
		if(emitir.isEmitir() == false)
			throw new ValidacionExcepcion("Certificado del emisor no se encuentra vigente");
		
		publico = this.archivoRepository.findbyCuentaCorrelacion(cuenta, emitir.getCertificado());
		privado = this.archivoRepository.findbyCuentaCorrelacion(cuenta, emitir.getPrivadoId());
		ret = this.emisorAssembler.toEmisor(emitir, publico, privado);
		
		return ret;
	}

}
