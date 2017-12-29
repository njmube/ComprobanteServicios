package com.cubetech.comprobante.servicios.application.impl;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.cubetech.comprobante.servicios.ObjetosTest;
import com.cubetech.comprobante.servicios.application.CancelarService;

public class CancelarServiceImplTest {

	private CancelarService cancelaService;

	public CancelarServiceImplTest(){

		try {
			this.cancelaService = new CancelarServiceImpl(JAXBContext.newInstance("com.cubetech.comprobante.servicios.application.xml.cancelacion"), null, null, null, null);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
	@Test
	public void testCancelaComprobante() {
		fail("Not yet implemented");
	}
*/
	@Test
	public void testGeneraXMl() {
		String xml;
		xml = this.cancelaService.generaXMl(ObjetosTest.CreaCancelacion());
		assertTrue(xml.length() > 0);
	}

}
