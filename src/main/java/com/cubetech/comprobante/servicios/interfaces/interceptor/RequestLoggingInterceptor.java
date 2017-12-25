package com.cubetech.comprobante.servicios.interfaces.interceptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClientException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		ClientHttpResponse response;
		try{
			 response = execution.execute(request, body);
		}catch(Exception exp){
			log.error("Errro de conexion: Url: {}, Metodo: {}, Body{}", request.getURI(), request.getMethod(), new String(body, Charset.forName("UTF-8")) ,exp);
			throw new RestClientException("Error de conexion");
		}
		if(log.isDebugEnabled())
			log.debug("request method: {}, request URI: {}, request headers: {}, request body: {}, response status code: {}, response headers: {}, response body: {}",
         request.getMethod(),
         request.getURI(),
         request.getHeaders(),
         new String(body, Charset.forName("UTF-8")),
         response.getStatusCode(),
         response.getHeaders(),
         new String(RequestLoggingInterceptor.getBytes(response.getBody()), Charset.forName("UTF-8")));
		else
			log.info("request method: {}, request URI: {} response status code: {} response body: {}",
					request.getMethod(),
	         request.getURI(),
	         response.getStatusCode(),
	         new String(RequestLoggingInterceptor.getBytes(response.getBody()), Charset.forName("UTF-8"))
					);

		return response;
	}
	
	 public static byte[] getBytes(InputStream is) throws IOException {

	    int len;
	    int size = 1024;
	    byte[] buf;

	    if (is instanceof ByteArrayInputStream) {
	      size = is.available();
	      buf = new byte[size];
	      len = is.read(buf, 0, size);
	    } else {
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      buf = new byte[size];
	      while ((len = is.read(buf, 0, size)) != -1)
	        bos.write(buf, 0, len);
	      buf = bos.toByteArray();
	    }
	    return buf;
	  }

}
