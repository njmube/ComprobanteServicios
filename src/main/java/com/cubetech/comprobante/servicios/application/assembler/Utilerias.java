package com.cubetech.comprobante.servicios.application.assembler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.cubetech.comprobante.servicios.application.error.ConvertirException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utilerias {
	public static boolean existeInfo(String valor){
		return valor != null && !valor.trim().isEmpty();
	}
	public static boolean existeInfo(List<?> lista){
		return lista != null && !lista.isEmpty();
	}
	public static boolean existeInfo(Map<?,?> map){
		return map != null && !map.isEmpty();
	}
	public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime fechaHora ) throws ConvertirException {
		try {
			DateTimeFormatter formatter  = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
			String fecha = fechaHora.withNano(0).format(formatter);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(fecha);
		} catch (DatatypeConfigurationException e) {
			log.error("Conversion a XMLGregorianCalendar" , e);
			throw new ConvertirException("Error en la conversion a XMLGregorianCalendar", e.getCause());
		}
	}
	public static LocalDateTime toLocalDateTime(XMLGregorianCalendar xmlfechaHora){
		LocalDateTime ret = null;
		
		ret = xmlfechaHora.toGregorianCalendar().toZonedDateTime().toLocalDateTime();
		
		return ret;
	}
	
}
