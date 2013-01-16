
package com.ideas.producerconsumer.rejection;

import com.ideas.producerconsumer.mediator.Queue;
import com.ideas.producerconsumer.resume.ResumeStrategy;
import com.ideas.producerconsumer.task.Task;

/**
 * Strategy to use when tasks are rejected by the {@link Queue}. Implementations can save the task data
 * and supply a {@link ResumeStrategy} to the {@link Queue} to resume the tasks once the queue is capable.
 * @author Varun Achar
 * @since 15-Jan-2013
 * @version 1.0
 */
public interface RejectionStrategy
{
	/**
	 * Operation to perform when a task is rejected by the {@link Queue}. Simply do nothing for cancelling all rejected tasks.
	 * @param r The rejected task.
	 */
	<T> void onReject(Task<T> r);
}
