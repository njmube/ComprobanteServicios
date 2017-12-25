//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2017.12.23 a las 05:57:51 PM CST 
//


package com.cubetech.comprobante.servicios.application.xml.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CancelaCFDResult" type="{http://cancelacfd.sat.gob.mx}Acuse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cancelaCFDResult"
})
@XmlRootElement(name = "CancelaCFDResponse", namespace = "http://cancelacfd.sat.gob.mx")
public class CancelaCFDResponse {

    @XmlElement(name = "CancelaCFDResult", namespace = "http://cancelacfd.sat.gob.mx")
    protected Acuse cancelaCFDResult;

    /**
     * Obtiene el valor de la propiedad cancelaCFDResult.
     * 
     * @return
     *     possible object is
     *     {@link Acuse }
     *     
     */
    public Acuse getCancelaCFDResult() {
        return cancelaCFDResult;
    }

    /**
     * Define el valor de la propiedad cancelaCFDResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Acuse }
     *     
     */
    public void setCancelaCFDResult(Acuse value) {
        this.cancelaCFDResult = value;
    }

}
