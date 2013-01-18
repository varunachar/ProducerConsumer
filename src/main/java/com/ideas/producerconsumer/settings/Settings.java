
package com.ideas.producerconsumer.settings;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ideas.producerconsumer.mediator.Queue;
import com.ideas.producerconsumer.util.PropertiesLoader;

/**
 * Settings that will be applied to the {@link Queue}. To set the configuration supply a queue.properties file in the classpath.
 * @author Varun Achar
 * @version 1.0
 * @since 17-Jan-2013
 */
public class Settings
{
	private final static Log	log					= LogFactory.getLog(Settings.class);
	private static Properties	settings;
	static
	{
		try
		{
			settings = PropertiesLoader.getPropertyFile("queue.properties");
		}
		catch(FileNotFoundException e)
		{
			log.error("Queue configuration not loaded.");
		}
	}
	/**
	 * the number of threads to keep in the pool, even if they are idle.
	 * In case more threads are needed, they will be created. Default 20.
	 */
	public static final int		CORE_POOL_SIZE		= Integer.parseInt(settings.getProperty("corePoolSize", "20"));
	
	/**
	 * Maximum threads that can be started for processing all tasks. Default 50.
	 */
	public static final int		MAX_POOL_SIZE		= Integer.parseInt(settings.getProperty("maxPoolSize", "50"));
	/**
	 * When the number of threads is greater than the core, this is the maximum time
	 * that excess idle threads will wait for new tasks before terminating. Default 60000.
	 * <b>Time in milliseconds</b>
	 */
	public static final long	KEEP_ALIVE_TIME		= Integer.parseInt(settings.getProperty("keepAliveTime", "60000"));
	/**
	 * Number to tasks that can be handled by the queue simultaneously.
	 */
	public static final int		CAPACITY			= Integer.parseInt(settings.getProperty("capacity", "50"));
	/**
	 * If the number to tasks currently submitted to the queue is below this level,
	 * the queue will start looking for previously failed tasks to complete. Default 30.
	 */
	public static final int		RESUME_THRESHOLD	= Integer.parseInt(settings.getProperty("resumeThreshold", "30"));
	
	/**
	 * Amount of time in milliseconds that the queue should wait for allowing perviously submitted tasks to complete once {@link Queue#shutdown()}
	 * has been called. Once this period has expires, the queue will force fully shutdown. Time in <b>milliseconds</b>. Default 60000.
	 */
	public static final long	TERMINATION_TIMEOUT	= Long.parseLong(settings.getProperty("terminationTimeout", "60000"));
}
