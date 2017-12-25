package com.cubetech.comprobante.servicios.application.assembler;

import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.CfdiUuid;

public class ComprobanteAssembler {
	public Comprobante toComprobante(String uuid){
		Comprobante ret = null;
		CfdiUuid cfdiUuid;
		
		if(Utilerias.existeInfo(uuid)){
			ret = new Comprobante();
			cfdiUuid = new CfdiUuid();
			cfdiUuid.setValor(uuid);
			ret.setUuid(cfdiUuid);
		}
		
		return ret;
	}
}
