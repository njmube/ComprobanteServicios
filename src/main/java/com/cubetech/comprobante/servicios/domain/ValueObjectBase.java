package com.cubetech.comprobante.servicios.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.MappedSuperclass;

import com.cubetech.comprobante.servicios.application.error.ValidacionExcepcion;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@MappedSuperclass
@EqualsAndHashCode
@ToString(includeFieldNames= false)
public abstract class ValueObjectBase {
	
		protected String valor;
/*		public ValueObjectBase(String valor){
			if(!Valido(getPattern(), valor))
				throw new ValidacionExcepcion("Patron no cumplido para el valor: " + valor);
			this.valor = valor;
		}
*/
		public String getvalor(){
			return this.valor;
		}
		public void setValor(String valor){
			if(!Valido(getPattern(), valor))
				throw new ValidacionExcepcion("Patron no cumplido para el valor: " + valor);
			this.valor = valor;
		}
		public abstract String getPattern();
		
		static public boolean Valido(String exprecion, String valor){
			boolean ret = false;
			Pattern patron = Pattern.compile(exprecion);
			Matcher matcher = patron.matcher(valor);
			ret = matcher.matches();
			return ret;
		}
		public boolean igual(String other){
			if (this.valor == other) return true;
			if (this.valor == null ? other != null : !this.valor.equals(other)) return false;
			return true;
		}
}
