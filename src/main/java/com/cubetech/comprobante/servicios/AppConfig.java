package com.cubetech.comprobante.servicios;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public JAXBContext jaxbContext(){
		try {
			return JAXBContext.newInstance("com.cubetech.comprobante.servicios.application.xml.cancelacion");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
