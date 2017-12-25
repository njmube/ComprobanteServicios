package com.cubetech.comprobante.servicios.application.impl;

import static org.junit.Assert.*;

import java.security.cert.CertificateException;

import org.junit.Test;

import com.cubetech.comprobante.servicios.ObjetosTest;
import com.cubetech.comprobante.servicios.application.FirmaDigitalService;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

public class FirmaDigitalServiceImplTest {

	private FirmaDigitalService firmaDigitalService;
	
	public static String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Cancelacion RfcEmisor=\"LAN7008173R5\" Fecha=\"2017-12-20T13:01:58\" xmlns=\"http://cancelacfd.sat.gob.mx\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"><Folios><UUID>3eaeabc9-ea41-4627-9609-c6856b78e2b1</UUID></Folios></Cancelacion>";
	
	public FirmaDigitalServiceImplTest(){
		this.firmaDigitalService = new FirmaDigitalServiceImpl();
	}
	
	@Test
	public void testFirmaXml() {
		Emisor emisor = null;
		String xmlFirmado = null;
		try {
			emisor = ObjetosTest.CreaEmisor();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		if(emisor != null){
			xmlFirmado = this.firmaDigitalService.firmaXml(XML, emisor);
		}
		assertNotNull(xmlFirmado);
		assertTrue(xmlFirmado.length() > 0);
	}
}
