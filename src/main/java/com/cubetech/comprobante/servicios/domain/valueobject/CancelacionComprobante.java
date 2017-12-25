package com.cubetech.comprobante.servicios.domain.valueobject;

import com.cubetech.comprobante.servicios.domain.ValueObject;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;

import lombok.Data;

@Data
public class CancelacionComprobante implements ValueObject<CancelacionComprobante> {

	private static final long serialVersionUID = 8392991802587835844L;
	
	private Comprobante comprobante;
	private EstadoCancelacion estado;
	
	@Override
	public boolean sameValueAs(CancelacionComprobante other) {
		return this.equals(other);
	}

}
