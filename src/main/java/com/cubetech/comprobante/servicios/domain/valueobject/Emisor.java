package com.cubetech.comprobante.servicios.domain.valueobject;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import com.cubetech.comprobante.servicios.domain.ValueObject;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Data
@ToString(exclude={"publicKey", "privateKey",  "posibleFirmar"})
@EqualsAndHashCode(exclude={"publicKey", "privateKey", "posibleFirmar"})
public class Emisor implements ValueObject<Emisor> {

	private static final long serialVersionUID = 8593086076368893838L;
	private Rfc rfc;
	private final X509Certificate publicKey;
	private final PrivateKey privateKey;
	private boolean posibleFirmar;
	
	@Override
	public boolean sameValueAs(Emisor other) {
		return rfc.sameValueAs(other.rfc);
	}

}
