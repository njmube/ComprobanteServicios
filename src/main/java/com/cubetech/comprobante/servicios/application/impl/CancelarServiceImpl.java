package com.cubetech.comprobante.servicios.application.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.cubetech.comprobante.servicios.application.CancelarService;
import com.cubetech.comprobante.servicios.application.FirmaDigitalService;
import com.cubetech.comprobante.servicios.application.assembler.CancelaCFDAssembler;
import com.cubetech.comprobante.servicios.application.error.InternalException;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.Cancelacion;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.entidad.repository.ComprobanteRepository;
import com.cubetech.comprobante.servicios.domain.valueobject.CancelacionComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;
import com.cubetech.comprobante.servicios.domain.valueobject.EstadoCancelacion;
import com.cubetech.comprobante.servicios.domain.valueobject.EstadoComprobante;
import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteCancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteXmlDTO;
import com.cubetech.comprobante.servicios.interfaces.restclient.ArchivoRepository;
import com.cubetech.comprobante.servicios.interfaces.restclient.CancelacionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CancelarServiceImpl implements CancelarService {

	private JAXBContext jaxbContext;
	private CancelaCFDAssembler cancelaCfdAssembler;
	private CancelacionService cancelacionService;
	private FirmaDigitalService firmaDigitalService;
	private ArchivoRepository archivoRepository;
	private ComprobanteRepository comprobanteRepository;
	
	@Autowired
	public CancelarServiceImpl(JAXBContext jaxbContext, 					CancelacionService cancelacionService, 
										FirmaDigitalService firmaDigitalService, 		ArchivoRepository archivoRepository,
										ComprobanteRepository comprobanteRepository){
		this.jaxbContext = jaxbContext;
		this.cancelaCfdAssembler = new CancelaCFDAssembler();
		this.cancelacionService = cancelacionService;
		this.archivoRepository = archivoRepository;
		this.comprobanteRepository = comprobanteRepository;
		this.firmaDigitalService = firmaDigitalService;
	}
	
	@Override
	public CancelacionComprobante cancelaComprobante(Comprobante comprobante, Emisor emisor) {
		String xmlCancelacion;
		Cancelacion cancelacion;
		ComprobanteCancelacionDTO responseCancelacion;
		ComprobanteXmlDTO requestCancelacion;
		CancelacionComprobante ret = new CancelacionComprobante();
		
		log.info("Incia el proceso Cancelacion UUID:{}", comprobante.getUuid());
		cancelacion = this.cancelaCfdAssembler.toCancelacion(comprobante, emisor);
		xmlCancelacion = this.generaXMl(cancelacion);
		log.info("Conversion completa XML");
		log.debug("Conversion xml: {}", xmlCancelacion);
		xmlCancelacion = this.firmaDigitalService.firmaXml(xmlCancelacion, emisor);
		log.debug("XML Firmado: {}", xmlCancelacion);
		
		requestCancelacion = new ComprobanteXmlDTO();
		requestCancelacion.setComprobante(xmlCancelacion);
		responseCancelacion = this.cancelacionService.cancelaComprobante(requestCancelacion);
		log.info("Finaliza la cancelacion, {}", responseCancelacion);
		
		if(responseCancelacion.getStatus().equals("OK")){
			if(responseCancelacion.getUuidStatus().equals("202") || responseCancelacion.getUuidStatus().equals("201")){
				comprobante.setEstado(EstadoComprobante.CANCELADO);
				comprobante.setRfc(emisor.getRfc());
				this.comprobanteRepository.save(comprobante);
				Runnable runnable2 = () -> {
					try{
						log.info("Inicio Guardado Acuse, {}", comprobante.getUuid());
						guardaAcuse(comprobante.getUuid().getvalor(), responseCancelacion.getAcuse());
						log.info("Guardado Acuse Correcto, {}",  comprobante.getUuid());
					}catch(Exception e){
						log.error("No se pudo guardar el archivo XML UUID:{}, XML:{}", comprobante.getUuid(), responseCancelacion.getAcuse());
					}
				};
				Thread thread2 = new Thread(runnable2);
				thread2.start();
			}
			ret.setComprobante(comprobante);
			ret.setEstado(EstadoCancelacion.setEstado(responseCancelacion.getUuidStatus()));
		}else{
			throw new InternalException("Error al Cancelar el comprobante " + responseCancelacion.getMessage());
		}
		
		return ret;
	}

	@Override
	public String generaXMl(Cancelacion cancelaCfd) {
		String ret = null;
		
		final Marshaller m;
		StringWriter sw = new StringWriter();
		
		try {
			m = this.jaxbContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			m.marshal(cancelaCfd, sw);
		  ret = sw.toString();
		  
		} catch (JAXBException e) {
			log.error("Error al crear el source para el JAXB ", e);
			throw new InternalException("Error al crear el source para el JAXB ", e.getCause());
		}
		if(log.isDebugEnabled())
			log.debug("XML Cancelacion \n{}", ret);
		return ret;
	}
	
	private void guardaAcuse(String uuid, String acuse) {
		ArchivoDTO archivoAcuse = new ArchivoDTO("ACUSE_XML", uuid + "_AcuseCancelacion" + ".xml", "", MediaType.APPLICATION_XML_VALUE, null);
		List<ArchivoDTO> archivos = new ArrayList<>();
		archivoAcuse.setContent(Base64.getEncoder().encodeToString(acuse.getBytes()));
		archivos.add(archivoAcuse);
		this.archivoRepository.save(uuid, archivos);
		
	}

}
