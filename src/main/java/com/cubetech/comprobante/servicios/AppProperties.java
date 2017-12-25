package com.cubetech.comprobante.servicios;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("app")
@Data
public class AppProperties {
	private String catalogourl;
	private String archivourl;
	private String emisorurl;
	private String timbreurl;
	private String impimirurl;
}
