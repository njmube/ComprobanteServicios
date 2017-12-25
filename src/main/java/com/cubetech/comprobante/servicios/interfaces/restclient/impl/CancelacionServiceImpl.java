package com.cubetech.comprobante.servicios.interfaces.restclient.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cubetech.comprobante.servicios.AppProperties;
import com.cubetech.comprobante.servicios.application.error.ConexionException;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteCancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteXmlDTO;
import com.cubetech.comprobante.servicios.interfaces.interceptor.RequestLoggingInterceptor;
import com.cubetech.comprobante.servicios.interfaces.restclient.CancelacionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CancelacionServiceImpl implements CancelacionService {

	private AppProperties properties;
  private RestTemplate rest;
  private static final String CANCELA ="/Cancela";
	
  @Autowired
  public CancelacionServiceImpl(AppProperties properties){
  	this.rest = new RestTemplate(getClientHttpRequestFactory());
    List<ClientHttpRequestInterceptor> tmp = new ArrayList<ClientHttpRequestInterceptor>();
    tmp.add(new RequestLoggingInterceptor());
    rest.setInterceptors(tmp);
  	
  	this.properties = properties;
  }
  
	@Override
	public ComprobanteCancelacionDTO cancelaComprobante(ComprobanteXmlDTO comprobante) {
		ComprobanteCancelacionDTO ret;
		final String url = properties.getTimbreurl() + CANCELA;
		HttpHeaders headers = HedersJson();
		ResponseEntity<ComprobanteCancelacionDTO> response;
		HttpEntity<ComprobanteXmlDTO> request = new  HttpEntity<>(comprobante, headers);
		
		try{
			response = rest.exchange(url, HttpMethod.POST, request, ComprobanteCancelacionDTO.class);
		}catch(RestClientException e){
			log.error("Timbrado URL {}", url ,e);
			throw new ConexionException("Error de conexion al ejecutar la cancelacion " + e.getMessage());
		}
		if(response.getStatusCode() == HttpStatus.OK){
			ret = response.getBody();
		}else{
			log.error("No fue posible cancelar la factura Url: {} CodigoRestpuetsa: {}", url, response.getStatusCode());
			throw new ConexionException("Error de conexion al ejecutar la cancelacion ");
		}
		
		return ret;
	}
	
	private static HttpHeaders HedersJson(){
		HttpHeaders headers =  new HttpHeaders();
		
		headers.add("Content-Type", "application/json");
    headers.add("Accept", "*/*");
		return headers;
	}
	
	private BufferingClientHttpRequestFactory getClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory clientHttpRequestFactory
      = new SimpleClientHttpRequestFactory();
    return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
	}

}
