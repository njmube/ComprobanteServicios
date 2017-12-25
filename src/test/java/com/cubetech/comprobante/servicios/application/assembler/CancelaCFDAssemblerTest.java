package com.cubetech.comprobante.servicios.application.assembler;

import static org.junit.Assert.*;

import java.security.cert.CertificateException;

import org.junit.Test;

import com.cubetech.comprobante.servicios.ObjetosTest;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.CancelaCFD;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

public class CancelaCFDAssemblerTest {

	CancelaCFDAssembler cancelaAssembler;
	
	public CancelaCFDAssemblerTest(){
		this.cancelaAssembler = new CancelaCFDAssembler();
	}
	
	@Test
	public void testToCancelaCFD() {
		Comprobante comprobante = ObjetosTest.CreaComprobante();
		Emisor emisor;
		CancelaCFD cancelaCfd = null;
		try {
			emisor = ObjetosTest.CreaEmisor();
			cancelaCfd = this.cancelaAssembler.toCancelaCFD(comprobante, emisor);
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		
		
		assertNotNull(cancelaCfd.getCancelacion());
		assertNotNull(cancelaCfd.getCancelacion().getFolios().get(0));
		assertTrue(cancelaCfd.getCancelacion().getFolios().get(0).getUUID().equals("3eaeabc9-ea41-4627-9609-c6856b78e2b1"));
	}
}
