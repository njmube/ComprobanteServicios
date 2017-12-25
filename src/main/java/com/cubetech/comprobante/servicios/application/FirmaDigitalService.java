package com.cubetech.comprobante.servicios.application;

import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;


public interface FirmaDigitalService {
	public String firmaXml(String xml, Emisor emisor);	
}
