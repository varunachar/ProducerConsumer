package com.ideas.producerconsumer.task;

import com.ideas.producerconsumer.mediator.Queue;

/**
 * Interface for all producers that are submitted to the {@link Queue}. The task can be tied to a type
 * of data and perform operations on the data.
 * @author Owner
 *
 * @param <T> The type of data on which the task will perform operation
 */
public interface Task<T> extends Runnable
{
	/**
	 * Get the data on which the task will perform it operation.
	 * @return
	 */
	T get();
	/**
	 * Set the data on which the task will perform its operation.
	 * @param t
	 */
	void set(T t);
}
