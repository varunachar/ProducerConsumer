package com.trelta.producerconsumer.mediator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trelta.producerconsumer.rejection.RejectionStrategy;
import com.trelta.producerconsumer.resume.ResumeStrategy;
import com.trelta.producerconsumer.settings.Settings;
import com.trelta.producerconsumer.task.Task;

/**
 * The mediating queue between all producers and consumers. This queue can be
 * used as a common queue for all producers throughout the application. The
 * capacity of the queue and the number of threads that can be spawned for
 * processing can be controlled.
 * 
 * If no {@link ThreadFactory} is specified, then
 * {@link Executors#defaultThreadFactory()} is used.
 * 
 * @author Varun Achar
 * @version 1.1.0
 * @since 1.0.0
 */
public class Queue
{
	private final Log				log					= LogFactory.getLog(getClass());
	private static final int		CORE_POOL_SIZE		= Settings.CORE_POOL_SIZE;
	private static final int		MAX_POOL_SIZE		= Settings.MAX_POOL_SIZE;
	private static final long		KEEP_ALIVE_TIME		= Settings.KEEP_ALIVE_TIME;
	private static final TimeUnit	TIME_UNIT			= TimeUnit.MILLISECONDS;
	private static final int		CAPACITY			= Settings.CAPACITY;
	private static final int		RESUME_THRESHOLD	= Settings.RESUME_THRESHOLD;

	private BlockingQueue<Runnable>	blockingQueue		= new ArrayBlockingQueue<Runnable>(CAPACITY);	;
	private ExecutorService			service;
	/**
	 * The strategy to use once the task has been rejected.
	 */
	private RejectionStrategy		rejectionStrategy;
	/**
	 * The strategy to use for restarting rejected tasks.
	 */
	private ResumeStrategy			resumeStrategy;
	/**
	 * A variable to maintain if the queue has started.
	 */
	private boolean					started				= false;

	/**
	 * Creates a queue without a {@link #resumeStrategy}. Failed tasks are
	 * rejected and passed to the {@link #rejectionStrategy}. Users are
	 * responsible for resuming tasks on their own.
	 * 
	 * @param rejectionStrategy
	 *            the strategy to use once a task has been rejected.
	 */
	public Queue(RejectionStrategy rejectionStrategy)
	{
		this(rejectionStrategy, null, Executors.defaultThreadFactory());
	}

	/**
	 * Queue defined with both a {@link #rejectionStrategy} and
	 * {@link #resumeStrategy}.
	 * 
	 * @param rejectionStrategy
	 * @param resumeStrategy
	 */
	public Queue(final RejectionStrategy rejectionStrategy, final ResumeStrategy resumeStrategy)
	{
		this(rejectionStrategy, resumeStrategy, Executors.defaultThreadFactory());
	}

	public Queue(final RejectionStrategy rejectionStrategy, final ResumeStrategy resumeStrategy, ThreadFactory factory)
	{
		this.rejectionStrategy = rejectionStrategy;
		this.resumeStrategy = resumeStrategy;
		service = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, this.blockingQueue, factory);
	}

	/**
	 * Start monitoring the queue. If queue size is less than
	 * {@link #RESUME_THRESHOLD}, {@link #resumeStrategy} will be called.
	 */
	public void start()
	{
		if (this.resumeStrategy == null)
		{
			this.started = true;
			return;
		}
		final Queue queue = this;
		Runnable run = new Runnable()
		{
			public void run()
			{
				while (!queue.service.isShutdown())
				{
					if ((queue.blockingQueue.size() <= RESUME_THRESHOLD))
					{
						resumeStrategy.resume(queue);
						try
						{
							Thread.sleep(queue.resumeStrategy.getResumeTime());
						}
						catch (InterruptedException e)
						{
							log.error("Unable to sleep the resume thread. Continuing");
						}
					}
				}
			}
		};
		synchronized (this)
		{
			if (!this.started)
			{
				this.service.submit(run);
				this.started = true;
			}
		}
		if (log.isDebugEnabled())
		{
			log.debug("Started resume listening");
		}
	}

	/**
	 * Submit a task to the Queue to execute.
	 * 
	 * @param task
	 *            The task to perform
	 * @return Future object representing the result of the executing the task
	 *         successfully.
	 */
	@SuppressWarnings("unchecked")
	public <T> Future<T> submit(Task<T> task)
	{
		try
		{
			return (Future<T>) service.submit(task);
		}
		catch (RejectedExecutionException e)
		{
			log.error("Task rejected because " + e.getMessage(), e);
			rejectionStrategy.onReject(task);
			return null;
		}
	}

	public void shutdown()
	{
		log.info("Service is shutting down.");
		service.shutdown();
		try
		{
			service.awaitTermination(Settings.TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e)
		{
			log.error("Could not await termination of queue because " + e.getMessage(), e);
		}
	}

	/**
	 * Set the executor service to use for managing tasks. By default a
	 * {@link ThreadPoolExecutor} with the settings defined in the
	 * queue.properties file is used.
	 * 
	 * @param service
	 */
	public void setService(ExecutorService service)
	{
		this.service = service;
	}

	public boolean isShutdown()
	{
		return service.isShutdown();
	}

	public int size()
	{
		return blockingQueue.size();
	}

}
