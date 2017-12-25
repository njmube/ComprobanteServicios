package com.cubetech.comprobante.servicios;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ComprobanteServiciosApplication {

	
	@PostConstruct
  public void init(){
      TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));   // It will set UTC timezone
      log.info("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
  }
	
	public static void main(String[] args) {
		SpringApplication.run(ComprobanteServiciosApplication.class, args);
	}
}
