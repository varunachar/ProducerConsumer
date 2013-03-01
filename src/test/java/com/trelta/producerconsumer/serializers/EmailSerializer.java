/**
 * 
 */

package com.trelta.producerconsumer.serializers;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.trelta.producerconsumer.object.Email;
import com.trelta.producerconsumer.object.SerializableEmail;
import com.trelta.producerconsumer.serialize.Serializer;

/**
 * @author Owner
 *
 */
public class EmailSerializer implements Serializer<Email, String>
{
	private final Log			log				= LogFactory.getLog(getClass());
	private final ObjectMapper	objectMapper	= new ObjectMapper();
	
	public String serialize(Email email)
	{
		String data = "";
		try
		{
			SerializableEmail serializableEmail = new SerializableEmail(email);
			System.out.println(serializableEmail.toString());
			data = objectMapper.writeValueAsString(serializableEmail);
		}
		catch(JsonGenerationException e)
		{
			log.error("Unable to convert email to json format", e);
		}
		catch(JsonMappingException e)
		{
			log.error("Unable to convert email to json format", e);
		}
		catch(IOException e)
		{
			log.error("Unable to serialize email. Disk operation failed", e);
		}
		return data;
	}
	
}
