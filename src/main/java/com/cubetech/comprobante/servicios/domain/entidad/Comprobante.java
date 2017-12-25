package com.cubetech.comprobante.servicios.domain.entidad;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cubetech.comprobante.servicios.domain.Entidad;
import com.cubetech.comprobante.servicios.domain.valueobject.CfdiUuid;
import com.cubetech.comprobante.servicios.domain.valueobject.EstadoComprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.Rfc;

import lombok.Data;


@Data
@Entity
public class Comprobante implements Entidad<Comprobante> {
	
	@Id
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="valor", column=@Column(name="uuid", nullable=false, length=100, unique=true)),
	})
	private CfdiUuid uuid;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="valor", column=@Column(name="rfc", nullable=true, length=15)),
	})
	private Rfc rfc;
	
	@Column(nullable = false)
	private EstadoComprobante estado;
	
	@Override
	public boolean sameIdentityAs(Comprobante other) {
		return this.equals(other);
	}

}
