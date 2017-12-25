package com.cubetech.comprobante.servicios.application.assembler;

import java.time.LocalDateTime;

import com.cubetech.comprobante.servicios.application.xml.cancelacion.CancelaCFD;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.Cancelacion;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.ObjectFactory;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

public class CancelaCFDAssembler {
	
	private ObjectFactory objectFactory;
	
	public CancelaCFDAssembler(){
		this.objectFactory = new ObjectFactory();
	}
	
	public CancelaCFD toCancelaCFD(Comprobante comprobante, Emisor emisor){
		CancelaCFD ret = null;
		if(comprobante != null){
			ret = this.objectFactory.createCancelaCFD();
			ret.setCancelacion(toCancelacion(comprobante, emisor));
		}
		return ret;
	}
	public Cancelacion toCancelacion(Comprobante comprobante, Emisor emisor){
		Cancelacion ret = null;
		LocalDateTime fecha;
		Cancelacion.Folios folio;
		
		if(comprobante != null && comprobante.getUuid() != null){
			ret = this.objectFactory.createCancelacion();
			fecha = LocalDateTime.now();
			ret.setFecha(Utilerias.toXMLGregorianCalendar(fecha));
			ret.setRfcEmisor(emisor.getRfc().getvalor());
			folio = this.objectFactory.createCancelacionFolios();
			folio.setUUID(comprobante.getUuid().getvalor());
			ret.getFolios().add(folio);
		}
		return ret;
	}
	
}
