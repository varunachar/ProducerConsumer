/**
 * 
 */

package com.ideas.producerconsumer.serializers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ideas.producerconsumer.object.Email;
import com.ideas.producerconsumer.object.EmailAttachment;
import com.ideas.producerconsumer.object.SerializableEmail;
import com.ideas.producerconsumer.object.SerializableEmailAttachment;
import com.ideas.producerconsumer.serialize.Deserializer;

/**
 * @author Owner
 *
 */
public class EmailDeserializer implements Deserializer<Email, String>
{
	private final Log			log				= LogFactory.getLog(getClass());
	
	private final ObjectMapper	objectMapper	= new ObjectMapper();
	
	public EmailDeserializer()
	{
		this.objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public Email deserialize(String v)
	{
		try
		{
			SerializableEmail serializableEmail = objectMapper.readValue(v, SerializableEmail.class);
			SerializableEmailAttachment[] attachments = serializableEmail.getAttachments();
			EmailAttachment[] emailAttachments = new EmailAttachment[attachments.length];
			for(int i = 0; i < attachments.length; i++)
			{
				SerializableEmailAttachment serializableEmailAttachment = attachments[i];
				EmailAttachment attachment = new EmailAttachment();
				attachment.setExtension(serializableEmailAttachment.getExtension());
				attachment.setName(serializableEmailAttachment.getName());
				try
				{
					attachment.setFile(serializableEmailAttachment.getFile());
				}
				catch(FileNotFoundException e)
				{
					log.error(e.getMessage(), e);
					continue;
				}
				emailAttachments[i] = attachment;
			}
			Email email = new Email();
			email.setAttachments(emailAttachments);
			email.setBcc(serializableEmail.getBcc());
			email.setCc(serializableEmail.getCc());
			email.setFrom(serializableEmail.getFrom());
			email.setMessage(serializableEmail.getMessage());
			email.setSubject(serializableEmail.getSubject());
			email.setTo(serializableEmail.getTo());
			return email;
		}
		catch(JsonParseException e)
		{
			log.error("Unable to parse serialized email ", e);
		}
		catch(JsonMappingException e)
		{
			log.error("Unable to parse serialized email ", e);
		}
		catch(IOException e)
		{
			log.error("Unable to parse serialized email ", e);
		}
		return null;
	}
	
}
