/**
 * 
 */
package com.software.estudialo.util;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class Encriptar.
 *
 * @author LUIS
 */
public class Encriptar {
	
	
	public static String toSHA256PasswordEncoder(String password) throws Exception {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        String encryptada = sb.toString();
        //System.out.println("Hex format : " + sb.toString());
      
		return encryptada;	
	}
	
	
	
	
	/**
	public static String toBCryptPasswordEncoder(String text) {
		
		int i = 0;
		String hashedPassword = "";
		
		while (i < 12) {
			String password = text;
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			hashedPassword = passwordEncoder.encode(password);
			i++;
		}
		
		return hashedPassword;
	}
	**/
	
	
	
}
