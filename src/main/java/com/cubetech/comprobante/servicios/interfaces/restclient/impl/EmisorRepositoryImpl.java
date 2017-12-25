package com.cubetech.comprobante.servicios.interfaces.restclient.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cubetech.comprobante.servicios.AppProperties;
import com.cubetech.comprobante.servicios.application.error.ConexionException;
import com.cubetech.comprobante.servicios.interfaces.dto.EmitirDTO;
import com.cubetech.comprobante.servicios.interfaces.interceptor.RequestLoggingInterceptor;
import com.cubetech.comprobante.servicios.interfaces.restclient.EmisorRepository;

@Repository
public class EmisorRepositoryImpl implements EmisorRepository {

	private static final Logger logger = LoggerFactory.getLogger(EmisorRepositoryImpl.class);
	private static final String EMISOR = "/Emitir";
	
	private AppProperties properties;
  private RestTemplate rest;

  @Autowired
  public EmisorRepositoryImpl(AppProperties properties) {
    this.rest = new RestTemplate(getClientHttpRequestFactory());
    List<ClientHttpRequestInterceptor> tmp = new ArrayList<ClientHttpRequestInterceptor>();
    tmp.add(new RequestLoggingInterceptor());
    rest.setInterceptors(tmp);
    
    this.properties = properties;
  }
  
	@Override
	public EmitirDTO consultaEmisor(String cuenta, String correlacion) throws ConexionException{
		EmitirDTO ret = null;
		final String url = properties.getEmisorurl() + EMISOR + "/" + correlacion;
		HttpHeaders headers = EmisorRepositoryImpl.HedersJson();
		ResponseEntity<EmitirDTO> response;
		
		headers.set("cuenta", cuenta);
		HttpEntity<EmitirDTO> request = new  HttpEntity<>(headers);
		
		try{
			response = rest.exchange(url, HttpMethod.GET, request, EmitirDTO.class);
		}catch(RestClientException e){
			logger.error("Consulta de Emisor URL {}", url ,e);
			throw new ConexionException("Error de conexion al consultar el Emisor " + e.getMessage());
		}
		
		if(response.getStatusCode() == HttpStatus.ACCEPTED || response.getStatusCode() == HttpStatus.OK){
			ret = response.getBody();
		}else{
			logger.error("No fue posible hacer la consulta del emisor Url: {} CodigoRestpuetsa: {}", url, response.getStatusCode());
			throw new ConexionException("Error de conexion al consultar el Emisor ");
		}
		return ret;
	}

	public static HttpHeaders HedersJson(){
		HttpHeaders headers =  new HttpHeaders();
		
		headers.add("Content-Type", "application/json");
    headers.add("Accept", "*/*");
    headers.add("Authorization","Basic dXNlcjo1RFtqWjRSXyRyOHdZLlNM");
		return headers;
	}
	
	private BufferingClientHttpRequestFactory getClientHttpRequestFactory() {
    //int timeout = 5000;
    
    SimpleClientHttpRequestFactory clientHttpRequestFactory
      = new SimpleClientHttpRequestFactory();
    //clientHttpRequestFactory.setConnectTimeout(timeout);
    //clientHttpRequestFactory.setReadTimeout(timeout * 2);
    return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
	}

}
