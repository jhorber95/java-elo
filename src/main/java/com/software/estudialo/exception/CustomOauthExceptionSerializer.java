/**
 * 
 */
package com.software.estudialo.exception;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author LUIS
 *
 */
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {
	
    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
//        gen.writeStringField("custom_error", value.getOAuth2ErrorCode());
//        gen.writeStringField("custom_error_description", value.getMessage());
        
//        gen.writeStringField("exito", "false");
        
//        System.out.println("Error code: " + value.getOAuth2ErrorCode());
//        System.out.println("Mensaje del service: " + value.getMessage());
        
        
        if (value.getOAuth2ErrorCode().equalsIgnoreCase("invalid_request")) {
        	if (value.getMessage().equalsIgnoreCase("Bad credentials")) {
        		gen.writeStringField("message", "credenciales_incorrectas");
			}        	
		}
        
        
      
        
        
//        if (value.getAdditionalInformation()!=null) {
//            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
//                String key = entry.getKey();
//                String add = entry.getValue();
//                gen.writeStringField(key, add);
//            }
//        }
        
        gen.writeEndObject();
    }

	
}
