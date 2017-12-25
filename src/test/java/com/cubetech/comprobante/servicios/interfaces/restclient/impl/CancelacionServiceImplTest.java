package com.cubetech.comprobante.servicios.interfaces.restclient.impl;
/*
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.cubetech.comprobante.servicios.AppProperties;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteCancelacionDTO;
import com.cubetech.comprobante.servicios.interfaces.dto.ComprobanteXmlDTO;
import com.cubetech.comprobante.servicios.interfaces.restclient.CancelacionService;

@RunWith(SpringRunner.class)
public class CancelacionServiceImplTest {

	private AppProperties properties;
	private CancelacionService cancelacionService;
	
	public CancelacionServiceImplTest(){
		this.properties = new AppProperties();
		this.properties.setTimbreurl("http://localhost:57781");
		this.cancelacionService = new CancelacionServiceImpl(this.properties);
	}
	
	@Test
	public void testCancelaComprobante() {
		ComprobanteXmlDTO comprobante = new ComprobanteXmlDTO();
		comprobante.setComprobante("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Cancelacion xmlns=\"http://cancelacfd.sat.gob.mx\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" Fecha=\"2017-12-20T13:01:58\" RfcEmisor=\"LAN7008173R5\"><Folios><UUID>3eaeabc9-ea41-4627-9609-c6856b78e2b1</UUID></Folios><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/><DigestValue>VDdkQ5++FHuDna00RLw+HynEBR0=</DigestValue></Reference></SignedInfo><SignatureValue>JuGJc2GYPUYrOVkF0L7eGgBSCJDECtYqUWr0KbTZfgWsaKLB+wr6CSIRkhtRG3DI564Eik6s35WxFjVdoDhcPy99obOtJV5WlLCLjV6r6XkoTKrXK5rGSCJUWekvXsw31ehSk0ykWnQzfW7OiC2CrJpgzMxCGEHrrjnhGkGJmkA+NFb3OeGg5d01X+tyVMVdRYskNJ9CNP44dn+zbgtYcNK0epyekAjVxNq169rm9WsmussLTvBvqjeGol68q09G5JFVQ0Smzp/4TZoejfOQ9qllspv3tDNXiMRndADdKozZNB5+eZ103vkeLTgHSy3Dvqwct1BZtEzAjznNB5Kmog==</SignatureValue><KeyInfo><X509Data><X509IssuerSerial><X509IssuerName>OID.1.2.840.113549.1.9.2=Responsable: ACDMA, OID.2.5.4.45=SAT970701NN3, L=Coyoacán, ST=Distrito Federal, C=MX, OID.2.5.4.17=06300, STREET=\"Av. Hidalgo 77, Col. Guerrero\", OID.1.2.840.113549.1.9.1=asisnet@pruebas.sat.gob.mx, OU=Administración de Seguridad de la Información, O=Servicio de Administración Tributaria, CN=A.C. 2 de pruebas(4096)</X509IssuerName><X509SerialNumber>286524172099382162235533054548081509963388170549</X509SerialNumber></X509IssuerSerial><X509Certificate>MIIFxTCCA62gAwIBAgIUMjAwMDEwMDAwMDAzMDAwMjI4MTUwDQYJKoZIhvcNAQELBQAwggFmMSAwHgYDVQQDDBdBLkMuIDIgZGUgcHJ1ZWJhcyg0MDk2KTEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMSkwJwYJKoZIhvcNAQkBFhphc2lzbmV0QHBydWViYXMuc2F0LmdvYi5teDEmMCQGA1UECQwdQXYuIEhpZGFsZ28gNzcsIENvbC4gR3VlcnJlcm8xDjAMBgNVBBEMBTA2MzAwMQswCQYDVQQGEwJNWDEZMBcGA1UECAwQRGlzdHJpdG8gRmVkZXJhbDESMBAGA1UEBwwJQ295b2Fjw6FuMRUwEwYDVQQtEwxTQVQ5NzA3MDFOTjMxITAfBgkqhkiG9w0BCQIMElJlc3BvbnNhYmxlOiBBQ0RNQTAeFw0xNjEwMjUyMTUyMTFaFw0yMDEwMjUyMTUyMTFaMIGxMRowGAYDVQQDExFDSU5ERU1FWCBTQSBERSBDVjEaMBgGA1UEKRMRQ0lOREVNRVggU0EgREUgQ1YxGjAYBgNVBAoTEUNJTkRFTUVYIFNBIERFIENWMSUwIwYDVQQtExxMQU43MDA4MTczUjUgLyBGVUFCNzcwMTE3QlhBMR4wHAYDVQQFExUgLyBGVUFCNzcwMTE3TURGUk5OMDkxFDASBgNVBAsUC1BydWViYV9DRkRJMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgvvCiCFDFVaYX7xdVRhp/38ULWto/LKDSZy1yrXKpaqFXqERJWF78YHKf3N5GBoXgzwFPuDX+5kvY5wtYNxx/Owu2shNZqFFh6EKsysQMeP5rz6kE1gFYenaPEUP9zj+h0bL3xR5aqoTsqGF24mKBLoiaK44pXBzGzgsxZishVJVM6XbzNJVonEUNbI25DhgWAd86f2aU3BmOH2K1RZx41dtTT56UsszJls4tPFODr/caWuZEuUvLp1M3nj7Dyu88mhD2f+1fA/g7kzcU/1tcpFXF/rIy93APvkU72jwvkrnprzs+SnG81+/F16ahuGsb2EZ88dKHwqxEkwzhMyTbQIDAQABox0wGzAMBgNVHRMBAf8EAjAAMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQsFAAOCAgEAJ/xkL8I+fpilZP+9aO8n93+20XxVomLJjeSL+Ng2ErL2GgatpLuN5JknFBkZAhxVIgMaTS23zzk1RLtRaYvH83lBH5E+M+kEjFGp14Fne1iV2Pm3vL4jeLmzHgY1Kf5HmeVrrp4PU7WQg16VpyHaJ/eonPNiEBUjcyQ1iFfkzJmnSJvDGtfQK2TiEolDJApYv0OWdm4is9Bsfi9j6lI9/T6MNZ+/LM2L/t72Vau4r7m94JDEzaO3A0wHAtQ97fjBfBiO5M8AEISAV7eZidIl3iaJJHkQbBYiiW2gikreUZKPUX0HmlnIqqQcBJhWKRu6Nqk6aZBTETLLpGrvF9OArV1JSsbdw/ZH+P88RAt5em5/gjwwtFlNHyiKG5w+UFpaZOK3gZP0su0sa6dlPeQ9EL4JlFkGqQCgSQ+NOsXqaOavgoP5VLykLwuGnwIUnuhBTVeDbzpgrg9LuF5dYp/zs+Y9ScJqe5VMAagLSYTShNtN8luV7LvxF9pgWwZdcM7lUwqJmUddCiZqdngg3vzTactMToG16gZA4CWnMgbU4E+r541+FNMpgAZNvs2CiW/eApfaaQojsZEAHDsDv4L5n3M1CC7fYjE/d61aSng1LaO6T1mh+dEfPvLzp7zyzz+UgWMhi5Cs4pcXx1eic5r7uxPoBwcCTt3YI1jKVVnV7/w=</X509Certificate></X509Data></KeyInfo></Signature></Cancelacion>");
		ComprobanteCancelacionDTO comprobanteCancelacion= this.cancelacionService.cancelaComprobante(comprobante);
		
		assertTrue(comprobanteCancelacion.getUuid().compareToIgnoreCase("3eaeabc9-ea41-4627-9609-c6856b78e2b1") == 0); 
	}

}
*/