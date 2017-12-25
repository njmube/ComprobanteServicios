package com.cubetech.comprobante.servicios;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import org.apache.commons.ssl.PKCS8Key;

import com.cubetech.comprobante.servicios.application.assembler.Utilerias;
import com.cubetech.comprobante.servicios.application.error.InternalException;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.CancelaCFD;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.Cancelacion;
import com.cubetech.comprobante.servicios.application.xml.cancelacion.ObjectFactory;
import com.cubetech.comprobante.servicios.domain.entidad.Comprobante;
import com.cubetech.comprobante.servicios.domain.valueobject.CfdiUuid;
import com.cubetech.comprobante.servicios.domain.valueobject.Emisor;
import com.cubetech.comprobante.servicios.domain.valueobject.Rfc;

public class ObjetosTest {
	
	public static ObjectFactory objectFactory = new ObjectFactory();
	public static String PASSWORD_CSD	=	"12345678a";
	public static String RFC = "LAN7008173R5";
	public static String UUID = "3eaeabc9-ea41-4627-9609-c6856b78e2b1";
	public static String PRIVATE_KEY = "MIIFDjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIAgEAAoIBAQACAggAMBQGCCqGSIb3DQMHBAgwggS9AgEAMASCBMh4EHl7aNSCaMDA1VlRoXCZ5UUmqErAbucRBAKNQXH8t1GNfLDIQejtcocS39VvWnpNXjZJeCg65Y2wI36UGn78gvnU0NOmyUkXksPVrkz7hqNtAVojPUtN65l+MVAsIRVD6OLJeKZ2bLx5z78zrx6Tp1zCGT/NpxL+CJSy5iY6TKqbJcK/9198noOvT2p8rKVqUUF3wLRvD6R/b3BC5wCon/exp3BUTZeiWJqGRRgaW4rn49ZbJPVIcDmUO8mojPesFHjJDSnA0nBnWaUvTYXi0srT+dLZOewsBR8d5GdSWh9ZkM29wJbjYHCMsXkObZjaap3YM8fU29zRyZ8KAqaCnBHCfYjbib56m+Lmnk+ScqMkQQ+S/+2pzn2LzauvBI4p/OjQgBDeblo22X7sX9OA9YaqB3q6CCjQ5tkDNrz3HOgTm+amh/kI8TEn9rcKf4Ru7mC1T7VMaFgBqpIS8YJNbcgegF0IF1FpCS05wjdU5CktYAnPnvC+Pj+MFDeH+184kIHBWqPNG6dAzALxRgtKTlGdJ1l5Do+4EWI+0mvKojREnKoDczFnDeCFnM51u3I9Vce3rkf0djRQKFomPVUnPDqxlR5lDAssYAYNcECAkvGxKcBDbjWi/6NHlwjS1r28+0Jhvfxjx9O6hi4AW82Q2/kBE5P/eOwln/jKSbLgi7Iyim1FFHxkQH1FY5kcKhAzFcIq85rGFlzHRfPF9OIQSmONI9kcWQCxkk8aG1u1zwbjZRYLTxlwmZvynOgaWRpTN8Y4ReBDIG1klhva7nqqoM416oXBG71IKaCtjAwRlE6pgaqnIz/WQAb2FR541pqynX6dB6DB1nIWnatsWZJZlu+Bnhf9DBlUsO9ZSAf9Fa9nJAzwFCzaKIsvGJIeKSZ/h+vInkjaO/rxswErVROTfZy1lO2CJ/xnAgzFGrpDxNJPliv3McO9TGwYy/zHhE6Pdo8Xu6NsMisNU6TB8Bc26uLNv/7kWhNmNnBA1qt5akln6hOHrPBXGBiTNUL0IoFVPNdCbS0834zAYXfgtZLDzVpeLqmeMpqXbIYK0/NXe9etxuOcN40O+B/fTHHmO7dMgBZ4vAApVQUPr7ilumVHsWSMRP/0p5R9q4qr1bDm9S5YCPevdyYWTSceGSrXHmjYzJLBtpc/s77mynNqZEYjhnKk2XRNp6kp/FYRu+QdsX9vaDJbLKR2EnSC4fU6UOTO03IZU15j3wOsg30QrXoKntSJ/beF99cvFHuPrQPWxCtws0lLwkkHNVOm6XNO948Moy1w1pL4i68CwmceYZaYrYhmHGdLuescFQrZQaULDWhpK2Stys8Vs/XwwxNi9MHAFSXpdy/b+Aro5n87w+0MHRcllF8ZKbtQ/ym4oG7aREuo7o71JXJQPjZKTOtVM1EQx/FLM/5brnDSoyvLtoYtv9/tTnIC+8gR6eErkzaGmn8pftPhGNuz6yzx8JeLFoMD7VWbGTefj46KS+yMweFJnpReHEqwnukXpEYq19EWVyQa/Sb7navtKt80y/vRs0aNZp3iL23AOs0u1kQ1CFNY2y12Gor1koaH2FUd5jAQnaSKmgarLy0H/QVvR2g8B3+Fh49QhKYrd8N6LvvI80cwbEoqYWn5DWA=";
	public static String PUBLIC_KEY = "MIIFxTCCA62gAwIBAgIUMjAwMDEwMDAwMDAzMDAwMjI4MTUwDQYJKoZIhvcNAQELBQAwggFmMSAwHgYDVQQDDBdBLkMuIDIgZGUgcHJ1ZWJhcyg0MDk2KTEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMSkwJwYJKoZIhvcNAQkBFhphc2lzbmV0QHBydWViYXMuc2F0LmdvYi5teDEmMCQGA1UECQwdQXYuIEhpZGFsZ28gNzcsIENvbC4gR3VlcnJlcm8xDjAMBgNVBBEMBTA2MzAwMQswCQYDVQQGEwJNWDEZMBcGA1UECAwQRGlzdHJpdG8gRmVkZXJhbDESMBAGA1UEBwwJQ295b2Fjw6FuMRUwEwYDVQQtEwxTQVQ5NzA3MDFOTjMxITAfBgkqhkiG9w0BCQIMElJlc3BvbnNhYmxlOiBBQ0RNQTAeFw0xNjEwMjUyMTUyMTFaFw0yMDEwMjUyMTUyMTFaMIGxMRowGAYDVQQDExFDSU5ERU1FWCBTQSBERSBDVjEaMBgGA1UEKRMRQ0lOREVNRVggU0EgREUgQ1YxGjAYBgNVBAoTEUNJTkRFTUVYIFNBIERFIENWMSUwIwYDVQQtExxMQU43MDA4MTczUjUgLyBGVUFCNzcwMTE3QlhBMR4wHAYDVQQFExUgLyBGVUFCNzcwMTE3TURGUk5OMDkxFDASBgNVBAsUC1BydWViYV9DRkRJMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgvvCiCFDFVaYX7xdVRhp/38ULWto/LKDSZy1yrXKpaqFXqERJWF78YHKf3N5GBoXgzwFPuDX+5kvY5wtYNxx/Owu2shNZqFFh6EKsysQMeP5rz6kE1gFYenaPEUP9zj+h0bL3xR5aqoTsqGF24mKBLoiaK44pXBzGzgsxZishVJVM6XbzNJVonEUNbI25DhgWAd86f2aU3BmOH2K1RZx41dtTT56UsszJls4tPFODr/caWuZEuUvLp1M3nj7Dyu88mhD2f+1fA/g7kzcU/1tcpFXF/rIy93APvkU72jwvkrnprzs+SnG81+/F16ahuGsb2EZ88dKHwqxEkwzhMyTbQIDAQABox0wGzAMBgNVHRMBAf8EAjAAMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQsFAAOCAgEAJ/xkL8I+fpilZP+9aO8n93+20XxVomLJjeSL+Ng2ErL2GgatpLuN5JknFBkZAhxVIgMaTS23zzk1RLtRaYvH83lBH5E+M+kEjFGp14Fne1iV2Pm3vL4jeLmzHgY1Kf5HmeVrrp4PU7WQg16VpyHaJ/eonPNiEBUjcyQ1iFfkzJmnSJvDGtfQK2TiEolDJApYv0OWdm4is9Bsfi9j6lI9/T6MNZ+/LM2L/t72Vau4r7m94JDEzaO3A0wHAtQ97fjBfBiO5M8AEISAV7eZidIl3iaJJHkQbBYiiW2gikreUZKPUX0HmlnIqqQcBJhWKRu6Nqk6aZBTETLLpGrvF9OArV1JSsbdw/ZH+P88RAt5em5/gjwwtFlNHyiKG5w+UFpaZOK3gZP0su0sa6dlPeQ9EL4JlFkGqQCgSQ+NOsXqaOavgoP5VLykLwuGnwIUnuhBTVeDbzpgrg9LuF5dYp/zs+Y9ScJqe5VMAagLSYTShNtN8luV7LvxF9pgWwZdcM7lUwqJmUddCiZqdngg3vzTactMToG16gZA4CWnMgbU4E+r541+FNMpgAZNvs2CiW/eApfaaQojsZEAHDsDv4L5n3M1CC7fYjE/d61aSng1LaO6T1mh+dEfPvLzp7zyzz+UgWMhi5Cs4pcXx1eic5r7uxPoBwcCTt3YI1jKVVnV7/w=";
	
	public static CfdiUuid CreaCfdiUuid(){
		CfdiUuid ret = new CfdiUuid();
		ret.setValor(UUID);
		return ret;
	}
	public static Comprobante CreaComprobante(){
		Comprobante ret = new Comprobante();
		ret.setUuid(CreaCfdiUuid());
		return ret;
	}
	
	public static Rfc CreaRfc(){
		Rfc ret = new Rfc();
		ret.setValor(RFC);
		return ret;
	}
	public static Emisor CreaEmisor() throws CertificateException{
		Emisor ret = new Emisor(CreaPublicKey(), CreaPrivateKey());
		ret.setRfc(CreaRfc());
		return ret;
	}
	
	public static X509Certificate CreaPublicKey() throws CertificateException{
		X509Certificate ret;
		try {
			ret = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(PUBLIC_KEY)));
		} catch (CertificateException e) {
			e.printStackTrace();
			throw e;
		}
		return ret;
	}
	
	public static PrivateKey CreaPrivateKey(){
		PrivateKey ret = null;
		PKCS8Key pkcs8= null;
		byte[] decrypted = null;
		PKCS8EncodedKeySpec spec = null;
		
		try{
			pkcs8 = new PKCS8Key( Base64.getDecoder().decode(PRIVATE_KEY), PASSWORD_CSD.toCharArray());
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
			e.printStackTrace();
			throw new InternalException(e.getCause());
		}
		return ret;
	}
	
	public static Cancelacion.Folios CreaCancelacionFolios(){
		Cancelacion.Folios ret = ObjetosTest.objectFactory.createCancelacionFolios();
		ret.setUUID(UUID);
		return ret;
	}
	
	public static Cancelacion CreaCancelacion(){
		Cancelacion ret = ObjetosTest.objectFactory.createCancelacion();
		
		ret.setFecha(Utilerias.toXMLGregorianCalendar(LocalDateTime.now()));
		ret.setRfcEmisor(RFC);
		ret.getFolios().add(CreaCancelacionFolios());
		
		return ret;
	}
	
	public static CancelaCFD CreaCancelaCFD(){
		CancelaCFD ret = ObjetosTest.objectFactory.createCancelaCFD();
		ret.setCancelacion(CreaCancelacion());
		return ret;
	}
	
}
