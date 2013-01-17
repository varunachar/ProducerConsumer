/**
 * 
 */

package com.ideas.producerconsumer.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Owner
 *
 */
public final class PropertiesLoader
{
	protected static Log	log	= LogFactory.getLog(PropertiesLoader.class);
	
	private PropertiesLoader() throws InstantiationException
	{
		throw new InstantiationException("Cannot instantiate this class");
	}
	
	/**
	 * Load the properties file of the specified name from the classpath.
	 * @param name The name of the properites file
	 * @return {@link Properties} loaded from the file
	 * @throws FileNotFoundException If properties file not found.
	 */
	public static final Properties getPropertyFile(String name) throws FileNotFoundException
	{
		if(name == null)
		{
			throw new IllegalArgumentException("no path specified");
		}
		
		InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(name);
		if(is == null)
		{
			throw new FileNotFoundException("Unable to load properties file " + name);
		}
		Properties props = new Properties();
		try
		{
			props.load(is);
		}
		catch(IOException e)
		{
			log.error(e);
			throw new FileNotFoundException("Unable to load properties file " + name);
		}
		return props;
	}
}
