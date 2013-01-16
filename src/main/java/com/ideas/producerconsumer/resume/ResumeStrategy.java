package com.ideas.producerconsumer.resume;

import com.ideas.producerconsumer.mediator.Queue;
import com.ideas.producerconsumer.task.Task;

/**
 * Strategy to use for resuming {@link Task}s which were rejected by the {@link Queue}
 * @author Varun Achar
 * @since 15-Jan-2013
 * @version 1.0
 */
public interface ResumeStrategy
{
	/**
	 * Resume is called everytime the time specified in {@link #getResumeTime()} elapses. Implementations should
	 * use this to restart failed tasks and passed when back to the {@link Queue}.
	 * @param queue The queue for submitted tasks.
	 */
	void resume(Queue queue);
	
	/**
	 * The time period after which the queue should look for any failed tasks. Essentially, the time after which the
	 * the {@link Queue} should call {@link #resume(Queue)} again.
	 * @return The time to restart resume.
	 */
	long getResumeTime();
}
