package com.cubetech.comprobante.servicios.domain.valueobject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cubetech.comprobante.servicios.application.error.ValidacionExcepcion;
import com.cubetech.comprobante.servicios.domain.ValueObject;
import com.cubetech.comprobante.servicios.domain.ValueObjectBase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Rfc extends ValueObjectBase implements ValueObject<Rfc> {

	private static final long serialVersionUID = -6906302880850813064L;
	
	private static final String EXP =  "^([A-Z&Ñ]{3,4})(\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01]))([A-Z\\d]{2})([A\\d])$";
	private static final String EXPFisica =  "^([A-Z&Ñ]{4})(\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01]))([A-Z\\d]{2})([A\\d])$";
	private static final String EXPMoral =  "^([A-Z&Ñ]{3})(\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01]))([A-Z\\d]{2})([A\\d])$";
	public static final String GENERICO = "XAXX010101000";
	public static final String EXTRANJERO = "XEXX010101000";
	
	private EnumPersonaFiscal tipoPersona;
	
	public Rfc(){
		
	}
	
	public Rfc(String valor) {
		this.setValor(valor);
	}
	
	@Override
	public boolean sameValueAs(Rfc other) {
		return super.equals(other);
	}

	@Override
	public String getPattern() {
		return EXP;
	}
	
	@Override
	public void setValor(String valor){
		try{
			if(!Valido(valor))
				throw new ValidacionExcepcion("Patron no cumplido para el valor: " + valor);
			this.valor = valor;
			calculaTipoRFC();
		}catch(ValidacionExcepcion e){
			log.error("Inicializar RFC valor:{} expresion:{}" , valor, EXP, e);
			throw new ValidacionExcepcion("Error de formato para el campo RFC");
		}
			
	}
	
	static public boolean Valido(String rfc){
		boolean ret = false;
		Pattern patron = Pattern.compile(EXP);
		Matcher matcher = patron.matcher(rfc);
		ret = matcher.matches();
		ret = ret && ValidaFecha(matcher.group(2));
		return ret;
	}
	static public boolean ValidaFecha(String fecha){
		boolean ret = false;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		Date currentDate = new Date();
		
		try{
			Date date = formatter.parse(fecha);
			ret = date.before(currentDate);
		}catch (ParseException|NullPointerException e){
			e.printStackTrace();
		}
		return ret;
	}
	private void calculaTipoRFC(){
		if(this.valor.equals(GENERICO))
			this.tipoPersona = EnumPersonaFiscal.GENERICA;
		else if(this.valor.equals(EXTRANJERO))
			this.tipoPersona = EnumPersonaFiscal.EXTRANGERO;
		else if( Pattern.matches(EXPFisica, this.valor))
			this.tipoPersona = EnumPersonaFiscal.FISICA;
		else if( Pattern.matches(EXPMoral, this.valor))
			this.tipoPersona = EnumPersonaFiscal.MORAL;
	}
	public EnumPersonaFiscal getTipoPersona(){
		return this.tipoPersona;
	}
}
