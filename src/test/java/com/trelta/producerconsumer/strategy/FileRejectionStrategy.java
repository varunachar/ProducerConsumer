
package com.trelta.producerconsumer.strategy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.trelta.producerconsumer.object.DatabaseObject;
import com.trelta.producerconsumer.object.Email;
import com.trelta.producerconsumer.rejection.RejectionStrategy;
import com.trelta.producerconsumer.serialize.Serializer;
import com.trelta.producerconsumer.serializers.EmailSerializer;
import com.trelta.producerconsumer.task.Task;

public class FileRejectionStrategy implements RejectionStrategy
{
	private final transient Log						log				= LogFactory.getLog(getClass());
	private static final Map<Class<?>, Serializer>	SERIALIZERS		= new HashMap<Class<?>, Serializer>();
	private ObjectMapper							objectMapper	= new ObjectMapper();
	ReentrantReadWriteLock							lock			= new ReentrantReadWriteLock();
	AtomicInteger									integer			= new AtomicInteger();
	static
	{
		SERIALIZERS.put(Email.class, new EmailSerializer());
	}
	
	public <T> void onReject(Task<T> r)
	{
		@SuppressWarnings("rawtypes")
		Serializer serializer = SERIALIZERS.get(r.get().getClass());
		Object data = serializer.serialize(r.get());
		DatabaseObject dbo = new DatabaseObject();
		dbo.setId(integer.incrementAndGet());
		dbo.setClazz(r.get().getClass().getCanonicalName());
		dbo.setData((String) data);
		this.save(dbo);
	}
	
	private void save(DatabaseObject dbo)
	{
		String bytes = null;
		try
		{
			bytes = objectMapper.writeValueAsString(dbo);
		}
		catch(JsonGenerationException e2)
		{
			e2.printStackTrace();
		}
		catch(JsonMappingException e2)
		{
			e2.printStackTrace();
		}
		catch(IOException e2)
		{
			e2.printStackTrace();
		}
		if(bytes == null)
		{
			return;
		}
		if(log.isDebugEnabled())
		{
			log.debug("writing to disk");
		}
		com.trelta.producerconsumer.storage.File.write(bytes.getBytes());
	}
	
}
