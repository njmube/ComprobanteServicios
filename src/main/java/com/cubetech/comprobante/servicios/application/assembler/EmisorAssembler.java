package com.cubetech.comprobante.servicios.application.assembler;

import com.cubetech.comprobante.servicios.application.impl.FirmaDigitalServiceImpl;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;
import com.cubetech.comprobante.servicios.domain.valueobject.Rfc;
import com.cubetech.comprobante.servicios.interfaces.dto.ArchivoDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.EmitirDTO;

public class EmisorAssembler {
	public Emisor toEmisor(EmitirDTO emitir, ArchivoDTO publico, ArchivoDTO privado){
		Emisor ret = null;
		Rfc rfc;
		
		if(emitir != null){
			ret = new Emisor(FirmaDigitalServiceImpl.obtenPublico(publico.getContent()), 
							FirmaDigitalServiceImpl.obtenPrivado(privado.getContent(), emitir.getPass()));
			ret.setPosibleFirmar(emitir.isEmitir());
			rfc = new Rfc();
			rfc.setValor(emitir.getRfc());
			ret.setRfc(rfc);
		}
		
		return ret;
	}
}
