package com.cubetech.comprobante.servicios.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cubetech.comprobante.servicios.interfaces.dto.CancelaDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.CancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.facade.ComprobanteFacadeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ServiciosController {
	
	private ComprobanteFacadeService comprobanteService;
	
	@Autowired
	public ServiciosController(ComprobanteFacadeService comprobanteService){
		this.comprobanteService = comprobanteService;
	}

	@RequestMapping(value="/Cancelar", method=RequestMethod.POST)
	public CancelacionDTO cancelaComprobantes(@RequestHeader(value="cuenta") String cuenta, @RequestBody CancelaDTO cancelaDto){
		CancelacionDTO ret = null;
		log.info("Iniciando SolicitudCancelacion {}", cancelaDto);
		ret = this.comprobanteService.cancelaComprobantes(cuenta, cancelaDto);
		log.info("Se completa SolicitudCancelacion {}", ret);
		return ret;
	}
}
