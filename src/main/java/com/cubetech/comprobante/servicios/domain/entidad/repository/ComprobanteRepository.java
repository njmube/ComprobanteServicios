package com.cubetech.comprobante.servicios.domain.entidad.repository;

import org.springframework.data.repository.Repository;

import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;

public interface ComprobanteRepository extends Repository<Comprobante, String> {
	public Comprobante save(Comprobante comp);
	public Comprobante findOneByUuid_Valor(String uuid);
}
