package com.cubetech.comprobante.servicios.application.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.ssl.PKCS8Key;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cubetech.comprobante.servicios.application.FirmaDigitalService;
import com.cubetech.comprobante.servicios.application.error.InternalException;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirmaDigitalServiceImpl implements FirmaDigitalService {

	@Override
	public String firmaXml(String xml, Emisor emisor) {
		String ret;
		//Convert xml en DOM
		Document doc = getXmlDocument(xml);
		//Create Xml SignatureFactory
		XMLSignatureFactory xmlSigFactory = XMLSignatureFactory.getInstance("DOM");
		DOMSignContext domSignCtx = new DOMSignContext(emisor.getPrivateKey(), doc.getDocumentElement());
		Reference ref = null;
    SignedInfo signedInfo = null;
		
    try {
      ref = xmlSigFactory.newReference("", xmlSigFactory.newDigestMethod(DigestMethod.SHA1, null),
              Collections.singletonList(xmlSigFactory.newTransform(Transform.ENVELOPED,
              (TransformParameterSpec) null)), null, null);
      signedInfo = xmlSigFactory.newSignedInfo(
              xmlSigFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
              (C14NMethodParameterSpec) null),
              xmlSigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
              Collections.singletonList(ref));
    } catch (NoSuchAlgorithmException|InvalidAlgorithmParameterException ex) {
    	log.error("Error al construir reference, SignedInfo, ", ex);
    	throw new InternalException("Error al construir reference, SignedInfo, ", ex);
    }
    //Generate the KeyInfo
    KeyInfo keyInfo = getKeyInfo(xmlSigFactory, emisor);
    //Create new XML Signature
    XMLSignature xmlSignature = xmlSigFactory.newXMLSignature(signedInfo, keyInfo);
    
    try {
      //Sign the document
      xmlSignature.sign(domSignCtx);
	  } catch (MarshalException|XMLSignatureException ex) {
	    log.error("Error al firmar el documento", ex);
	    throw new InternalException("Error al firmar el documento", ex);
	  } 
    ret = combierteSignedXml(doc);
		
		return ret;
	}

	private Document getXmlDocument(String xml) throws InternalException{
		Document ret = null;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    
    try {
			ret =  dbf.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
		} catch (SAXException|IOException|ParserConfigurationException e) {
			log.error("Error al generar el Document xml: {}", xml, e);
			throw new InternalException("Convertir xml en DOM ", e);
		} 
    
    return ret;
	}
	
	private KeyInfo getKeyInfo(XMLSignatureFactory xmlSigFactory, Emisor emisor){
		KeyInfo keyInfo = null;
		
		KeyInfoFactory keyInfoFact = xmlSigFactory.getKeyInfoFactory();
		X509IssuerSerial isser;
    List<Object> x509Content = new ArrayList<>();
    X509Data data;
     
    String issuerName = emisor.getPublicKey().getIssuerX500Principal().getName("RFC1779");
    BigInteger serialNumber = emisor.getPublicKey().getSerialNumber();
    
    isser = keyInfoFact.newX509IssuerSerial(issuerName, serialNumber);
    x509Content.add(isser);
    x509Content.add(emisor.getPublicKey());
    data = keyInfoFact.newX509Data(x509Content);
    keyInfo = keyInfoFact.newKeyInfo(Collections.singletonList(data));
		
		return keyInfo;
	}
	private String combierteSignedXml(Document doc) {
		String ret = null;
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory transFactory = TransformerFactory.newInstance();
    Transformer trans = null;
    
    try{
      trans = transFactory.newTransformer();
	  }catch (TransformerConfigurationException ex) {
	  	log.error("Error al generar Transformer",  ex);
			throw new InternalException("Error al generar Transformer ", ex);
	  }
	  try{
	    trans.transform(new DOMSource(doc), result);
	    ret = writer.toString();
	  }catch (TransformerException ex) {
	  	log.error("Error al generar el XML firmado",  ex);
			throw new InternalException("Error al generar el XML firmado ", ex);
	  }
	  if(log.isDebugEnabled())
	  	log.debug("XmlFirmado, {}", ret);
    
		log.info("Xml firmado de manera correcta ...");
		return ret;
	}

	
	public static X509Certificate obtenPublico(String certificado) throws InternalException {
		X509Certificate ret;
		try {
			ret = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(certificado)));
		} catch (CertificateException e) {
			log.error("Error al obtener PublicKey ", e);
			throw new InternalException("Erro al obtener el PublicKey ", e);
		}
		return ret;
	}

	
	public static PrivateKey obtenPrivado(String certificado, String password) throws InternalException {
		PrivateKey ret = null;
		PKCS8Key pkcs8= null;
		byte[] decrypted = null;
		PKCS8EncodedKeySpec spec = null;
		
		try{
			pkcs8 = new PKCS8Key( Base64.getDecoder().decode(certificado), password.toCharArray());
		  decrypted = pkcs8.getDecryptedBytes();
		  spec = new PKCS8EncodedKeySpec( decrypted );
		 
		  if ( pkcs8.isDSA() )
		  {
		  	ret = KeyFactory.getInstance( "DSA" ).generatePrivate( spec );
		  }
		  else if ( pkcs8.isRSA() )
		  {
		  	ret = KeyFactory.getInstance( "RSA" ).generatePrivate( spec );
		  }
		  ret = pkcs8.getPrivateKey();
		}catch(Exception e){
			log.error("Error al obtener PrivateKey ", e);
			throw new InternalException("Error al obtener el PrivateKey ", e);
		}
		return ret;
	}

}
