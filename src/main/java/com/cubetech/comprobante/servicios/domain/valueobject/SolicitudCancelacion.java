package com.cubetech.comprobante.servicios.domain.valueobject;

import java.util.List;

import com.cubetech.comprobante.servicios.domain.ValueObject;

import lombok.Data;

@Data
public class SolicitudCancelacion implements ValueObject<SolicitudCancelacion> {

	private static final long serialVersionUID = -6415432120139552329L;
	List<CancelacionComprobante> cancelaciones;
	
	@Override
	public boolean sameValueAs(SolicitudCancelacion other) {
		return this.equals(other);
	}

}
