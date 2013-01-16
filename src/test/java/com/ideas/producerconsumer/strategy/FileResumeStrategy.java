
package com.ideas.producerconsumer.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import com.ideas.producerconsumer.mediator.Queue;
import com.ideas.producerconsumer.object.DatabaseObject;
import com.ideas.producerconsumer.object.Email;
import com.ideas.producerconsumer.producer.EmailProducer;
import com.ideas.producerconsumer.resume.ResumeStrategy;
import com.ideas.producerconsumer.serialize.Deserializer;
import com.ideas.producerconsumer.serializers.EmailDeserializer;
import com.ideas.producerconsumer.task.Task;

public class FileResumeStrategy implements ResumeStrategy
{
	private final transient Log								log				= LogFactory.getLog(getClass());
	private final static Map<Class<?>, Deserializer<?, ?>>	DESERIALIZERS	= new HashMap<Class<?>, Deserializer<?, ?>>();
	private final static Map<String, String>				PRODUCERS		= new HashMap<String, String>();
	private final File										file			= new File("C:\\Users\\Owner\\Desktop\\rejected.txt");
	private ObjectMapper									objectMapper	= new ObjectMapper();
	ReentrantReadWriteLock									lock			= new ReentrantReadWriteLock();
	static
	{
		DESERIALIZERS.put(Email.class, new EmailDeserializer());
		PRODUCERS.put(Email.class.getCanonicalName(), EmailProducer.class.getCanonicalName());
	}
	
	public FileResumeStrategy()
	{
		this.objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public void resume(Queue queue)
	{
		//System.out.println("Stared resume");
		List<DatabaseObject> objects = fetch();
		for(Object element : objects)
		{
			DatabaseObject object = (DatabaseObject) element;
			try
			{
				Class<?> forName = Class.forName(object.getClazz());
				Deserializer deserializer = DESERIALIZERS.get(forName);
				Object data = deserializer.deserialize(object.getData());
				//Create producer and submit to queue
				String producerClassName = PRODUCERS.get(data.getClass().getCanonicalName());
				Class<?> producerClass = Class.forName(producerClassName);
				Task<Object> producerInstance = (Task<Object>) producerClass.newInstance();
				producerInstance.set(data);
				queue.submit(producerInstance);
			}
			catch(ClassNotFoundException e)
			{
				log.error("Unable to deserialize object ", e);
			}
			catch(InstantiationException e)
			{
				log.error("Unable to initialize producer object ", e);
			}
			catch(IllegalAccessException e)
			{
				log.error("Unable to initialize producer object ", e);
			}
		}
	}
	
	private List<DatabaseObject> fetch()
	{
		List<DatabaseObject> objects = new ArrayList<DatabaseObject>();
		String[] lines = com.ideas.producerconsumer.storage.File.readAndDelete(10);
		for(String line : lines)
		{
			if(line != null)
			{
				try
				{
					DatabaseObject convertValue = objectMapper.readValue(line, DatabaseObject.class);
					objects.add(convertValue);
				}
				catch(Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return objects;
	}
	
	public long getResumeTime()
	{
		return TimeUnit.SECONDS.toMillis(10);
	}
	
}
